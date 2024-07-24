package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.mapper.SysMenuMapper;
import com.sys.manager.mapper.SysRoleMapper;
import com.sys.manager.entity.SysMenu;
import com.sys.manager.entity.SysRole;
import com.sys.manager.pojo.User;
import com.sys.manager.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public R<?> addSysMenu(SysMenu menu) {
        User adminInfo = userService.getUserInfo();
        menu.setCreateTime(new Date());
        menu.setCreator(adminInfo.getId());
        sysMenuMapper.insert(menu);
        return R.ok();
    }

    @Override
    public R<?> updateSysMenu(SysMenu menu) {
        User adminInfo = userService.getUserInfo();
        menu.setUpdateTime(new Date());
        menu.setUpdater(adminInfo.getId());
        sysMenuMapper.updateById(menu);
        return R.ok();
    }

    @Override
    public R<?> findSysMenuList(SysMenu menuParam) {
        //先获取全部的菜单
        Wrapper<SysMenu> queryWrapper = Wrappers.query(menuParam).orderByAsc("m_order");
        List<SysMenu> rootSysSysMenu = sysMenuMapper.selectList(queryWrapper);
        // 最后的结果
        List<SysMenu> menuList = new ArrayList<>();
        // 先找到所有的一级菜单
        for (SysMenu menu : rootSysSysMenu) {
            // 一级菜单没有parentId
            if (menu.getParentId() == null) {
                menuList.add(menu);
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysMenu menu : menuList) {
            menu.setParentId(0);
            menu.setChildren(getChild(menu.getId(), rootSysSysMenu));
        }
        return R.ok(menuList);
    }

    @Override
    public R<?> deleteSysMenu(Integer id) {
        sysMenuMapper.deleteById(id);
        return R.ok();
    }

    @Override
    public R<?> menuRoleBinding(SysRole sysRole) {
        User adminInfo = userService.getUserInfo();
        sysRole.setUpdater(adminInfo.getId());
        sysRole.setUpdateTime(new Date());
        sysRoleMapper.updateById(sysRole);
        return R.ok();
    }

    @Override
    public R<?> findSysMenuOptionList() {
        List<SysMenu> menuOption = sysMenuMapper.selectOption();
        // 最后的结果
        List<SysMenu> menuList = new ArrayList<>();
        // 先找到所有的一级菜单
        for (SysMenu menu : menuOption) {
            // 一级菜单没有parentId
            if (menu.getParentId() == null) {
                menuList.add(menu);
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysMenu menu : menuList) {
            menu.setChildren(getChild(menu.getId(), menuOption));
        }
        return R.ok(menuList);
    }

    @Override
    public R<?> selectList() {
        LambdaQueryWrapper<SysMenu> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(SysMenu::getStatus, '1');
        List<SysMenu> sysMenus = sysMenuMapper.selectList(lambdaQueryWrapper);
        return R.ok(sysMenus);
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    public List<SysMenu> getChild(Integer id, List<SysMenu> rootMenu) {
        // 子菜单
        List<SysMenu> childList = new ArrayList<>();
        for (SysMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId() != null && menu.getParentId().equals(id)) {
                childList.add(menu);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (SysMenu menu : childList) {
            // 直接递归
            menu.setChildren(getChild(menu.getId(), rootMenu));
        } // 递归退出条件
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }

}
