package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.manager.domain.R;
import com.sys.manager.security.SecurityService;
import com.sys.manager.mapper.SysRoleMapper;
import com.sys.manager.entity.SysRole;
import com.sys.manager.pojo.User;
import com.sys.manager.service.SysRoleService;
import com.sys.manager.utils.IPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public R<?> findAdminRolesList(SysRole sysRole, Integer page, Integer size) {
        IPage<SysRole> sysRolePage = new Page<>(page, size);
        Wrapper<SysRole> queryWrapper = Wrappers.query(sysRole);
        sysRolePage = sysRoleMapper.selectPage(sysRolePage, queryWrapper);
        return R.ok(IPages.buildDataMap(sysRolePage));
    }

    @Override
    public R<?> updateAdminRoles(SysRole sysRole) {
        User user = userService.getUserInfo();
        if (sysRole.getRoleId() != null) {
            //修改
            sysRole.setUpdater(user.getId());
            sysRole.setUpdateTime(new Date());
            sysRoleMapper.updateById(sysRole);
        } else {  //新增
            sysRole.setCreator(user.getId());
            sysRole.setCreateTime(new Date());
            sysRoleMapper.insert(sysRole);
        }
        return R.ok();
    }

    @Override
    public R<?> deleteAdminRole(Integer id) {
        sysRoleMapper.deleteById(id);
        return R.ok();
    }

    @Override
    public R<?> findRoleOption() {
        List<Map<String, Object>> list = sysRoleMapper.selectOption();
        return R.ok(R.buildDataMap(list));
    }

}
