package com.sys.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sys.manager.domain.R;
import com.sys.manager.entity.SysMenu;
import com.sys.manager.entity.SysRole;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
public interface SysMenuService extends IService<SysMenu> {

    R<?> addSysMenu(SysMenu menu);

    R<?> updateSysMenu(SysMenu menu);

    R<?> findSysMenuList(SysMenu menu);

    R<?> deleteSysMenu(Integer id);

    R<?> menuRoleBinding(SysRole sysRole);

    R<?> findSysMenuOptionList();

    R<?> selectList();
}
