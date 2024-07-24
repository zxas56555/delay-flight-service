package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.entity.AdminInfo;
import com.sys.manager.entity.FlightInfo;
import com.sys.manager.mapper.FlightInfoMapper;
import com.sys.manager.mapper.PassengerInfoMapper;
import com.sys.manager.entity.PassengerInfo;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.PassengerInfoService;
import com.sys.manager.utils.DateUtils;
import com.sys.manager.utils.ExcelReader;
import com.sys.manager.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 旅客信息表(PassengerInfo)表服务实现类
 *
 * @author makejava
 * @since 2024-07-23 13:35:08
 */
@Service
public class PassengerInfoServiceImpl extends ServiceImpl<PassengerInfoMapper, PassengerInfo> implements PassengerInfoService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PassengerInfoMapper passengerInfoMapper;

    @Autowired
    private FlightInfoMapper flightInfoMapper;

    @Override
    public R<?> selectPassenger(PassengerInfo passenger, Integer page, Integer size) {
        IPage<PassengerInfo> iPage = new Page<>(page, size);
        iPage = passengerInfoMapper.selectPassenger(iPage, passenger);
        return R.ok(iPage);
    }

    @Override

    public R<?> importPassenger(MultipartFile file) {
        Integer loginUserId = securityService.getLoginUserId();
        //检查文件格式
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        //截取文件的类型符
        String subString = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!("xls".equals(subString) || "xlsx".equals(subString))) {
            return R.fail("文件格式不正确！");
        }
        List<PassengerInfo> putList = new ArrayList<>();
        try {
            putList = ExcelReader.getPassengerList(file.getInputStream(), subString);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail(501,"文件上传异常！");
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //查询航班信息
        LambdaQueryWrapper<FlightInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(FlightInfo::getDataStatus, "1");
        List<FlightInfo> flightInfos = flightInfoMapper.selectList(wrapper);
        // 组装map。 key：航班号+乘机日期（MU6359-2024-07-01）。
        Map<String, FlightInfo> flightMap = new HashMap<>();
        for (FlightInfo flightInfo : flightInfos) {
            String str = format.format(flightInfo.getFlightDate());
            flightMap.put(flightInfo.getFlightNum() + "-" + str, flightInfo);
        }

        /**
         * 读取bean列表，导入到数据库中
         */
        List<Map<String, Object>> errorList = new ArrayList<>();
        int add = 0, upd = 0, keep = 0;
        for (PassengerInfo passenger : putList) {
            String flightDateStr = passenger.getFlightDateStr();
            String flightNum = passenger.getFlightNum();
            String name = passenger.getName();
            String cardId = passenger.getCardId();
            String shippingSpace = passenger.getShippingSpace();
            String seat = passenger.getSeat();
            if (StringUtils.isEmpty(flightDateStr) || StringUtils.isEmpty(flightNum) || StringUtils.isEmpty(name)
                    || StringUtils.isEmpty(cardId) || StringUtils.isEmpty(shippingSpace) || StringUtils.isEmpty(seat)) {
                errorList.add(errorMapBuilder(passenger, "信息填写不完整"));
                continue;
            }
            try {
                // 补充信息：
                // 乘机时间
                Date flightDate = DateUtils.stringToDate(flightDateStr, "yyyy-MM-dd");
                if (flightDate == null) {
                    errorList.add(errorMapBuilder(passenger, "乘机时间格式不正确"));
                    continue;
                }
                passenger.setFlightDate(flightDate);
                // 性别
                int i = cardId.charAt(16) - '0';
                if (i % 2 == 0) {
                    passenger.setSex("女");
                } else {
                    passenger.setSex("男");
                }
                // 登机口、登机时间、始发站、目的站
                FlightInfo flightInfo = flightMap.get(flightNum + "-" + flightDateStr);
                if (flightInfo == null) {
                    errorList.add(errorMapBuilder(passenger, "未查到航班信息"));
                    continue;
                }
                passenger.setBoardingGate(flightInfo.getBoardingGate());
                passenger.setBoardingTime(flightInfo.getBoardingTime());
                passenger.setStartCity(flightInfo.getStartCity());
                passenger.setArriveCity(flightInfo.getArriveCity());
                // 根据身份证号+航班号+乘机时间  查询并更新信息
                PassengerInfo exist = passengerInfoMapper.selectExist(flightNum, flightDateStr, cardId);
                // 如果是未处理，则更新信息， 若已处理，则不进行操作
                if (exist == null) {
                    passenger.setCreator(loginUserId);
                    passengerInfoMapper.insert(passenger);
                    add++;
                } else if ("1".equals(exist.getIsDeal())) {
                    keep++;
                } else if ("0".equals(exist.getIsDeal())) {
                    passenger.setId(exist.getId());
                    passenger.setUpdater(loginUserId);
                    passengerInfoMapper.updateById(passenger);
                    upd++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorList.add(errorMapBuilder(passenger, "保存失败"));
            }
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("errorList", errorList);
        returnMap.put("add", add);
        returnMap.put("upd", upd);
        returnMap.put("keep", keep);
        return R.ok(returnMap);
    }

    private Map<String, Object> errorMapBuilder(PassengerInfo raw, String errorText) {
        return new HashMap<String, Object>(4) {{
            put("name", raw.getName());
            put("cardId", raw.getCardId());
            put("flightNum", raw.getFlightNum());
            put("flightDateStr", raw.getFlightDateStr());
            put("errorText", errorText);
        }};
    }

    @Override
    public R<?> savePassenger(PassengerInfo passenger) {
        Integer loginUserId = securityService.getLoginUserId();
        PassengerInfo exist = passengerInfoMapper.selectExist(passenger.getFlightNum(), passenger.getFlightDateStr(), passenger.getCardId());
        if (exist == null) {
            passenger.setCreator(loginUserId);
            passengerInfoMapper.insert(passenger);
            return R.ok();
        } else {
            return R.fail("已扫描该乘客");
        }
    }

}

