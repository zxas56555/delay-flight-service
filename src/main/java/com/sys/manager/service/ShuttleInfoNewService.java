package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.AirportShuttle;
import com.sys.manager.entity.ShuttleInfoNew;

public interface ShuttleInfoNewService extends IService<ShuttleInfoNew> {

    R<?> selecetShuttleNew(ShuttleInfoNew shuttleInfoNew, Integer page, Integer pageSize);

    R<?> selectDictNew(String name);

    R<?> saveShuttleNew(ShuttleInfoNew shuttleInfoNew);

    R<?> delShuttleNew(Integer id);

    R<?> isGoNew(AirportShuttle airShuttle);

    R<?> airShuttleInfoNew(Integer airShuttleId);
}
