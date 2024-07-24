package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.ShuttleInfo;

/**
 * 摆渡车信息表(ShuttleInfo)表服务接口
 *
 * @author makejava
 * @since 2024-07-23 13:35:33
 */
public interface ShuttleInfoService extends IService<ShuttleInfo> {

    R<?> selectShuttle(ShuttleInfo shuttleInfo, Integer page, Integer size);

    R<?> selectDict(String name);

    R<?> saveShuttle(ShuttleInfo shuttleInfo);

    R<?> delShuttle(Integer id);

}

