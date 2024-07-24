package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.AdminInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author dw
 * @since 2023-01-30
 */
public interface AdminInfoService extends IService<AdminInfo> {

    R<?> findAdminList(AdminInfo adminInfo, Integer page, Integer size);

    R<?> saveAdmin(AdminInfo adminInfo);

    R<?> deleteAdmin(Integer id);

    R<?> resetPass(Integer id);

    R<?> importAdminInfo(MultipartFile file) throws IOException;
}
