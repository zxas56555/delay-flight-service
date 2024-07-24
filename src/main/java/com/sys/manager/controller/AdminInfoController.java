package com.sys.manager.controller;

import com.sys.manager.domain.R;
import com.sys.manager.service.AdminInfoService;
import com.sys.manager.entity.AdminInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@RestController
@RequestMapping("/api/adminInfo")
@Api(tags = "系统用户管理接口")
public class AdminInfoController {

    @Autowired
    private AdminInfoService adminInfoService;

    /**
     * 角色crud
     */
    @GetMapping
    @ApiOperation(value = "查询管理员列表", httpMethod = "GET")
    public R<?> findAdminList(AdminInfo adminInfo,
                              @RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer size) {
        return adminInfoService.findAdminList(adminInfo, page, size);
    }

    @PostMapping("/save")
    @ApiOperation(value = "修改/新增管理员信息", httpMethod = "POST")
    public R<?> saveAdmin(@RequestBody AdminInfo adminInfo) {
        return adminInfoService.saveAdmin(adminInfo);
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除管理员", httpMethod = "POST")
    public R<?> deleteAdmin(@RequestParam Integer id) {
        return adminInfoService.deleteAdmin(id);
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入用户文件", httpMethod = "POST")
    public R<?> importAdminInfo(@RequestPart MultipartFile file) throws IOException {
        return adminInfoService.importAdminInfo(file);
    }

}

