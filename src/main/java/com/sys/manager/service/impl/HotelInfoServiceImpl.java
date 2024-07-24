package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.entity.ShuttleInfo;
import com.sys.manager.mapper.HotelInfoMapper;
import com.sys.manager.entity.HotelInfo;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.HotelInfoService;
import com.sys.manager.utils.IPages;
import com.sys.manager.utils.StringUtils;
import com.sys.manager.utils.text.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 酒店信息表(HotelInfo)表服务实现类
 *
 * @author makejava
 * @since 2024-07-23 13:34:52
 */
@Service
public class HotelInfoServiceImpl extends ServiceImpl<HotelInfoMapper, HotelInfo> implements HotelInfoService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HotelInfoMapper hotelInfoMapper;

    @Override
    public R<?> selectHotel(HotelInfo hotelInfo, Integer page, Integer size) {
        IPage<HotelInfo> iPage = new Page<>(page, size);
        LambdaQueryWrapper<HotelInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotEmpty(hotelInfo.getHotelName()), HotelInfo::getHotelName, hotelInfo.getHotelName())
                .like(StringUtils.isNotEmpty(hotelInfo.getContacts()), HotelInfo::getContacts, hotelInfo.getContacts())
                .eq(HotelInfo::getDataStatus, "1");
        iPage = hotelInfoMapper.selectPage(iPage, wrapper);
        return R.ok(IPages.buildDataMap(iPage));
    }

    @Override
    public R<?> selectDict(String name) {
        LambdaQueryWrapper<HotelInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotEmpty(name), HotelInfo::getHotelName, name)
                .eq(HotelInfo::getDataStatus, "1");
        List<HotelInfo> list = hotelInfoMapper.selectList(wrapper);
        return R.ok(list);
    }

    @Override
    public R<?> saveHotel(HotelInfo hotelInfo) {
        Integer loginUserId = securityService.getLoginUserId();
        if (hotelInfo.getId() == null) {
            hotelInfo.setCreator(loginUserId);
            hotelInfoMapper.insert(hotelInfo);
        } else {
            hotelInfo.setUpdater(loginUserId);
            hotelInfoMapper.updateById(hotelInfo);
        }
        return R.ok();
    }

    @Override
    public R<?> delHotel(Integer id) {
        HotelInfo hotel = hotelInfoMapper.selectById(id);
        Integer loginUserId = securityService.getLoginUserId();
        hotel.setDataStatus(UUID.getUUID());
        hotel.setUpdater(loginUserId);
        hotelInfoMapper.updateById(hotel);
        return R.ok();
    }

}

