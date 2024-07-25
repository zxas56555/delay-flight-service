package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.PassengerInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 旅客信息表(PassengerInfo)表服务接口
 *
 * @author makejava
 * @since 2024-07-23 13:35:08
 */
public interface PassengerInfoService extends IService<PassengerInfo> {

    R<?> selectPassenger(PassengerInfo passenger, Integer page, Integer size);

    R<?> selectGroupHotel(PassengerInfo passenger);

    R<?> importPassenger(MultipartFile file);

    R<?> savePassenger(PassengerInfo passenger);

}

