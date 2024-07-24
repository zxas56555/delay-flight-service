package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.entity.SysMenu;
import com.sys.manager.entity.SysRole;
import com.sys.manager.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@RestController
@RequestMapping("/api/sysMenu")
@Api(tags = "菜单管理接口")
public class SysMenuController {

    @Autowired
    private SysMenuService menuService;

    /**
     * 菜单crud
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加菜单信息", httpMethod = "POST")
    public R<?> addSysMenu(@RequestBody SysMenu menu) {
        return menuService.addSysMenu(menu);
    }

    @PostMapping("/upd")
    @ApiOperation(value = "修改菜单信息", httpMethod = "POST")
    public R<?> updateSysMenu(@RequestBody SysMenu menu) {
        return menuService.updateSysMenu(menu);
    }


    @GetMapping
    @ApiOperation(value = "查询菜单列表", httpMethod = "GET")
    public R<?> findSysMenuList(SysMenu menu) {
        return menuService.findSysMenuList(menu);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除菜单", httpMethod = "POST")
    public R<?> deleteSysMenu(@RequestParam Integer id) {
        return menuService.deleteSysMenu(id);
    }


    /**
     * 菜单角色绑定
     */
    @PostMapping("/role/binding")
    @ApiOperation(value = "菜单角色绑定", httpMethod = "POST")
    public R<?> menuRoleBinding(@RequestBody SysRole sysRole) {
        return menuService.menuRoleBinding(sysRole);
    }

    /**
     * 查询菜单列表(id,title,enable)
     *
     * @return
     */
    @GetMapping("/option")
    @ApiOperation(value = "查询菜单列表(id,title)", httpMethod = "GET")
    public R<?> findSysMenuOptionList() {
        return menuService.findSysMenuOptionList();
    }


    @GetMapping("/selectList")
    @ApiOperation(value = "查询菜单（非树形结构）", httpMethod = "GET")
    public R<?> selectList() {
        return menuService.selectList();
    }
}

