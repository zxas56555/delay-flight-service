package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.mapper.ShuttleInfoMapper;
import com.sys.manager.entity.ShuttleInfo;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.ShuttleInfoService;
import com.sys.manager.utils.IPages;
import com.sys.manager.utils.StringUtils;
import com.sys.manager.utils.text.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 摆渡车信息表(ShuttleInfo)表服务实现类
 *
 * @author makejava
 * @since 2024-07-23 13:35:33
 */
@Service
public class ShuttleInfoServiceImpl extends ServiceImpl<ShuttleInfoMapper, ShuttleInfo> implements ShuttleInfoService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ShuttleInfoMapper shuttleInfoMapper;

    @Override
    public R<?> selectShuttle(ShuttleInfo shuttleInfo, Integer page, Integer size) {
        IPage<ShuttleInfo> iPage = new Page<>(page, size);
        LambdaQueryWrapper<ShuttleInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotEmpty(shuttleInfo.getShuttleNo()), ShuttleInfo::getShuttleNo, shuttleInfo.getShuttleNo())
                .like(StringUtils.isNotEmpty(shuttleInfo.getName()), ShuttleInfo::getName, shuttleInfo.getName())
                .eq(shuttleInfo.getSeatNum() != null, ShuttleInfo::getSeatNum, shuttleInfo.getSeatNum())
                .like(StringUtils.isNotEmpty(shuttleInfo.getHeadPeople()), ShuttleInfo::getHeadPeople, shuttleInfo.getHeadPeople())
                .eq(ShuttleInfo::getDataStatus, "1");
        iPage = shuttleInfoMapper.selectPage(iPage, wrapper);
        return R.ok(IPages.buildDataMap(iPage));
    }

    @Override
    public R<?> selectDict(String name) {
        LambdaQueryWrapper<ShuttleInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotEmpty(name), ShuttleInfo::getName, name)
                .eq(ShuttleInfo::getDataStatus, "1");
        List<ShuttleInfo> list = shuttleInfoMapper.selectList(wrapper);
        return R.ok(list);
    }

    @Override
    public R<?> saveShuttle(ShuttleInfo shuttleInfo) {
        Integer loginUserId = securityService.getLoginUserId();
        if (shuttleInfo.getId() == null) {
            shuttleInfo.setCreator(loginUserId);
            shuttleInfoMapper.insert(shuttleInfo);
        } else {
            shuttleInfo.setUpdater(loginUserId);
            shuttleInfoMapper.updateById(shuttleInfo);
        }
        return R.ok();
    }

    @Override
    public R<?> delShuttle(Integer id) {
        ShuttleInfo shuttle = shuttleInfoMapper.selectById(id);
        Integer loginUserId = securityService.getLoginUserId();
        shuttle.setDataStatus(UUID.getUUID());
        shuttle.setUpdater(loginUserId);
        shuttleInfoMapper.updateById(shuttle);
        return R.ok();
    }

}

