package com.sys.manager.controller;

import com.sys.manager.annotation.PreAuthorize;
import com.sys.manager.domain.R;
import com.sys.manager.entity.AdminInfo;
import com.sys.manager.pojo.User;
import com.sys.manager.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 1.0.0  2020/10/21 15:47
 * @since JDK1.8
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "系统用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/admin/login")
    @ApiOperation(value = "管理员用户登录", httpMethod = "POST")
    public R<?> adminLogin(String account, String password) {
        return userService.adminLogin(account, password);
    }

//    /**
//     * 验证授权
//     *
//     * @return
//     */
//    @PostMapping("/checkExpire")
//    @ApiOperation(value = "验证授权", httpMethod = "POST")
//    public R<?> checkExpire() {
//        return userService.checkExpire();
//    }

//    /**
//     * 添加授权
//     *
//     * @param empowerKey
//     * @return
//     */
//    @PostMapping("/addExpire")
//    @ApiOperation(value = "添加授权", httpMethod = "POST")
//    public R<?> addExpire(String empowerKey) {
//        return userService.addExpire(empowerKey);
//    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @PostMapping("/admin/info")
    @ApiOperation(value = "获取当前登录用户信息", httpMethod = "POST")
    public R<User> getAdminInfo() {
        return userService.getAdminInfo();
    }

    /**
     * 获取权限菜单
     * @return
     */
    @PostMapping("/admin/menu")
    @ApiOperation(value = "获取权限菜单", httpMethod = "POST")
    public R<?> getAdminMenu() {
        return userService.getAdminMenu();
    }

    @PostMapping("/admin/logout")
    @ApiOperation(value = "退出登录", httpMethod = "POST")
    public R<?> logout(){
        return userService.logout();
    }

    /**
     * 获取验证码
     *
     * @param request
     * @param resp
     * @return
     * @throws IOException
     */
    @GetMapping("/verifyCode")
    @ApiOperation(value = "获取验证码", httpMethod = "GET")
    public R<?> getVerifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        return userService.getVerifyCode();
    }

    /**
     * 修改密码
     * @return
     * @version 1.0.0  2020/10/20 13:26
     * @since JDK1.8
     */
    @PostMapping("/admin/pass")
    @ApiOperation(value = "管理员用户修改密码", httpMethod = "POST")
    public R<?> editAdminPass(String oldPassword,String newPassword) {
        return userService.editAdminPass(oldPassword,newPassword);
    }

    @PostMapping("/updateSelfInfo")
    @ApiOperation(value = "管理员用户修改个人信息", httpMethod = "POST")
//    @PreAuthorize(hasPerm = "sys:add")
    public R<?> updateSelfInfo(@RequestBody AdminInfo adminInfo){
        return  userService.updateSelfInfo(adminInfo);
    }

    @GetMapping("/select/selectNameByUserId")
    @ApiOperation(value = "按条件查询用户名称", httpMethod = "GET")
    public String selectNameByUserId(@RequestParam String userId){
        return userService.selectNameByUserId(userId);
    }

//    @GetMapping(value = "/sendMail")
//    @ApiOperation(value = "发送找回密码邮件", httpMethod = "GET")
//    public R<?> sendMail(String toMail) {
//        return userService.sendMail(toMail);
//    }

//    @GetMapping(value = "/checkVerificationCode")
//    @ApiOperation(value = "检查邮件验证码是否正确", httpMethod = "GET")
//    @OperateInfo(value = "找回密码", type = "找回密码")
//    public R<?> checkVerificationCode(String code,String toMail) {
//        return userService.checkVerificationCode(code,toMail);
//    }

}
