package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.SysRole;
import com.sys.manager.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@RestController
@RequestMapping("/api/sysRole")
@Api(tags = "角色管理接口")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 角色crud
     */
    @GetMapping
    @ApiOperation(value = "查询角色列表", httpMethod = "GET")
    public R<?> findAdminRolesList(SysRole sysRole,
                                   @RequestParam(required = false, defaultValue = "1") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size) {
        return sysRoleService.findAdminRolesList(sysRole, page, size);
    }

    @PostMapping("/save")
    @ApiOperation(value = "修改/新增角色信息", httpMethod = "POST")
    public R<?> updateAdminRoles(@RequestBody SysRole sysRole) {
        return sysRoleService.updateAdminRoles(sysRole);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除角色", httpMethod = "POST")
    public R<?> deleteAdminRole(@RequestParam Integer id) {
        return sysRoleService.deleteAdminRole(id);
    }

    /**
     * 查询角色option
     */
    @GetMapping("/option")
    @ApiOperation(value = "查询角色option", httpMethod = "GET")
    public R<?> findRoleOption() {
        return sysRoleService.findRoleOption();
    }

}


