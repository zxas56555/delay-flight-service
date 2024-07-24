package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.mapper.AdminInfoMapper;
import com.sys.manager.mapper.UnitInfoMapper;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.AdminInfoService;
import com.sys.manager.utils.*;
import com.sys.manager.entity.AdminInfo;
import com.sys.manager.pojo.AdminInfoResult;
import com.sys.manager.utils.text.UUID;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@Service
public class AdminInfoServiceImpl extends ServiceImpl<AdminInfoMapper, AdminInfo> implements AdminInfoService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UnitInfoMapper unitInfoMapper;

    @Override
    public R<?> findAdminList(AdminInfo adminInfo, Integer page, Integer size) {
        IPage<AdminInfoResult> adminInfoPage = new Page<>(page, size);
        adminInfoPage = adminInfoMapper.selectByPageAndCon(adminInfoPage, adminInfo);
        return R.ok(IPages.buildDataMap(adminInfoPage));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> saveAdmin(AdminInfo adminInfo) {
        Integer userId = securityService.getLoginUserId();
        if (adminInfo.getId() != null) {
            // 修改
            //身份证号校验
            if (!StringUtils.isEmpty(adminInfo.getCardId())) {
                boolean idNumber = IDUtils.check(adminInfo.getCardId());
                if (!idNumber) {
                    return R.fail("身份证号填写错误！");
                }
            }
            //手机号校验
            if(!StringUtils.isEmpty(adminInfo.getPhone())){
                boolean phone = PhoneNumberValidator.validatePhoneNumber(adminInfo.getPhone());
                if(!phone){
                    return R.fail("手机号填写错误！");
                }
            }
            adminInfo.setUpdater(userId);
            adminInfoMapper.updateById(adminInfo);
        } else {
            //新增
            //身份证号校验
            if (!StringUtils.isEmpty(adminInfo.getCardId())) {
                boolean idNumber = IDUtils.check(adminInfo.getCardId());
                if (!idNumber) {
                    return R.fail("身份证号填写错误！");
                }
            }
            //手机号校验
            if(!StringUtils.isEmpty(adminInfo.getPhone())){
                boolean phone = PhoneNumberValidator.validatePhoneNumber(adminInfo.getPhone());
                if(!phone){
                    return R.fail("手机号填写错误！");
                }
            }
            if (StringUtils.isEmpty(adminInfo.getName())) {
                return R.fail("姓名或邮箱不能为空");
            }
            AdminInfo queryParam = adminInfoMapper.selectExist(adminInfo.getName());
            if (queryParam != null) {
                return R.fail("姓名已被注册");
            }
            if (StringUtils.isEmpty(adminInfo.getPhone())) {
                return R.fail("手机号不能为空");
            }
            adminInfo.setCreator(userId);
            adminInfo.setCreateTime(new Date());
            adminInfo.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
            String password = "df@123456";
            String phone = adminInfo.getPhone();
            if (phone.length() >= 6) {
                password = "df@" + adminInfo.getPhone().substring(adminInfo.getPhone().length() - 6);
            }
            password = MD5Util.getMD5(password);
            adminInfo.setPassword(password);
            adminInfoMapper.insert(adminInfo);
        }
        return R.okMsg("保存成功");
    }

    @Override
    public R<?> deleteAdmin(Integer id) {
        AdminInfo adminInfo = adminInfoMapper.selectAdminById(id);
        adminInfo.setStatus(UUID.getUUID());
        adminInfoMapper.updateById(adminInfo);
        return R.ok("删除成功");
    }

    @Override
    public R<?> resetPass(Integer id) {
        AdminInfo adminInfo = adminInfoMapper.selectAdminById(id);
        //重置密码
        String password = "df@123456";
        String phone = adminInfo.getPhone();
        if (phone.length() >= 6) {
            password = "df@" + adminInfo.getPhone().substring(adminInfo.getPhone().length() - 6);
        }
        String pwd = MD5Util.getMD5(password);
        int i = adminInfoMapper.updatePassById(pwd, id);
        if (i > 0) {
            return R.ok(password);
        } else {
            return R.fail("重置失败");
        }
    }

    private void setUserNameAndPassWord(AdminInfo adminInfo) {
        adminInfo.setStatus("2");
        String name = adminInfo.getName();
        // 创建pinyin4j处理类
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        // 输出设置，大小写、音标方式
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 无音调
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        char[] chars = name.toCharArray();
        String userName = "";
        try {
            userName = PinyinHelper.toHanyuPinyinString(String.valueOf(chars[0]), defaultFormat, "");
            for (int i = 1; i < chars.length; i++) {
                userName = userName + PinyinHelper.toHanyuPinyinString(String.valueOf(chars[i]), defaultFormat, "").substring(0, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adminInfo.setUserName(userName);
        String password = "df@123456";
        String phone = adminInfo.getPhone();
        if (phone.length() >= 6) {
            password = "df@" + adminInfo.getPhone().substring(adminInfo.getPhone().length() - 6);
        }
        adminInfo.setPassword(MD5Util.getMD5(password));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<?> importAdminInfo(MultipartFile file) throws IOException {
        Integer loginUserId = securityService.getLoginUserId();
        //检查文件格式
        String fileName = file.getOriginalFilename();
        assert fileName != null;
        //截取文件的类型符
        String subString = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!("xls".equals(subString) || "xlsx".equals(subString))) {
            return R.fail("文件格式不正确！");
//            throw new ClassCastException("文件格式不正确！");
        }
        List<AdminInfo> putList = new ArrayList<>();
        try {
            putList = ExcelReader.getAdminList(file.getInputStream(), subString);
        } catch (IOException e) {
            e.printStackTrace();
            return R.fail(501,"文件上传异常！");
//            throw new CustomException(ResultStatusEnum.UPLOAD_EXCEPTION);
        }
        /**
         * 读取bean列表，导入到数据库中
         */
        List<Map<String, Object>> errorList = new ArrayList<>();
        int add = 0;
        for (AdminInfo adminInfo : putList) {
            if (StringUtils.isNotEmpty(adminInfo.getName()) && StringUtils.isNotEmpty(adminInfo.getPhone())) {
                try {
                    // 判断username是否重复；如果不重复就直接插入，如果重复就update（根据id）
                    LambdaQueryWrapper<AdminInfo> lambdaQueryWrapper = Wrappers.lambdaQuery();
                    lambdaQueryWrapper
                            .eq(adminInfo.getName() != null, AdminInfo::getName, adminInfo.getName());
                    AdminInfo queryParam = adminInfoMapper.selectOne(lambdaQueryWrapper);
                    if (queryParam != null) {
                        errorList.add(errorMapBuilder(adminInfo, "该用户名已存在"));
                        continue;
                    }
                    //身份证号校验
                    if (!StringUtils.isEmpty(adminInfo.getCardId())) {
                        boolean idNumber = IDUtils.check(adminInfo.getCardId());
                        if (!idNumber) {
                            errorList.add(errorMapBuilder(adminInfo, "身份证号填写错误"));
                        }
                    }
                    //手机号校验
                    if (!StringUtils.isEmpty(adminInfo.getPhone())) {
                        boolean phone = PhoneNumberValidator.validatePhoneNumber(adminInfo.getPhone());
                        if (!phone) {
                            errorList.add(errorMapBuilder(adminInfo, "手机号填写错误"));
                        }
                    }
//                    LambdaQueryWrapper<AdminInfo> queryWrapper = Wrappers.lambdaQuery();
//                    queryWrapper.eq(adminInfo.getMail() != null, AdminInfo::getMail, adminInfo.getMail());
//                    AdminInfo param = adminInfoMapper.selectOne(queryWrapper);
//                    if (param != null) {
//                        errorList.add(errorMapBuilder(adminInfo, "该邮箱已被占用！"));
//                        continue;
//                    }
                    adminInfo.setCreator(loginUserId);
                    //设置用户的用户名和密码
                    setUserNameAndPassWord(adminInfo);
                    adminInfoMapper.insert(adminInfo);
                    add++;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorList.add(errorMapBuilder(adminInfo, "数据库插入数据失败"));
                }
            } else {
                errorList.add(errorMapBuilder(adminInfo, "必填项为空"));
            }

        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("errorList", errorList);
        returnMap.put("add", add);
        return R.ok(returnMap);
    }

    private Map<String, Object> errorMapBuilder(AdminInfo raw, String errorText) {
        return new HashMap<String, Object>(4) {{
            put("name", raw.getName());
            put("cardId", raw.getCardId());
            put("errorText", errorText);
        }};
    }

}
