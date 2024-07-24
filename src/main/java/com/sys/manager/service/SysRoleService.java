package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.SysRole;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
public interface SysRoleService extends IService<SysRole> {

    R<?> findAdminRolesList(SysRole sysRole, Integer page, Integer size);

    R<?> updateAdminRoles(SysRole sysRole);

    R<?> deleteAdminRole(Integer id);

    R<?> findRoleOption();

}
