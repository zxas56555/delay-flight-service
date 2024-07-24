package com.sys.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sys.manager.constant.Constants;
import com.sys.manager.domain.R;
import com.sys.manager.enums.ResultStatusEnum;
import com.sys.manager.exception.CustomException;
import com.sys.manager.mapper.*;
import com.sys.manager.security.SecurityService;
import com.sys.manager.service.AdminInfoService;
import com.sys.manager.service.UserService;
import com.sys.manager.utils.*;
import com.sys.manager.enums.UserStatusEnum;
import com.sys.manager.utils.BeanUtils;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sys.manager.entity.AdminInfo;
import com.sys.manager.entity.SysMenu;
import com.sys.manager.pojo.AdminNameResult;
import com.sys.manager.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.sys.manager.constant.CacheConstants.LOGIN_TOKEN_KEY;
import static com.sys.manager.constant.Constants.EXPIRES_TIME;

/**
 * @author Ray
 * @since JDK1.8
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private AdminInfoService adminInfoService;

    @Autowired
    private UnitInfoMapper unitInfoMapper;

//    @Value("${spring.mail.username}")
//    private String from;

    @Override
    public R<?> adminLogin(String userName, String password) {
        // 判断账号密码是否正确
        Wrapper<AdminInfo> queryWrapper = Wrappers.query(new AdminInfo(userName, password));
//        AdminInfo adminInfo = adminInfoMapper.selectOne(queryWrapper);
        AdminInfo adminInfo = adminInfoMapper.selectByUserAndPass(userName, password);

        if (adminInfo != null) {
            String ok = UserStatusEnum.OK.getCode();
            if (UserStatusEnum.NOCREATE.getCode().equals(adminInfo.getStatus())) {
                throw new CustomException(ResultStatusEnum.ACCOUNT_NOT_CREATE);
            }
            if (!ok.equals(adminInfo.getStatus())) {
                throw new CustomException(ResultStatusEnum.HAS_BEEN_PULLED_BLACK);
            }
            // 正确登录返回token
            Map<String, String> claims = new HashMap<>();
            claims.put("id", adminInfo.getId().toString());
            claims.put("name", adminInfo.getName());

            String token = JwtUtils.createToken(claims);
            // 用户信息存入缓存
            User user = new User();
            BeanUtils.copyProperties(adminInfo, user);
            String menuIds = sysRoleMapper.selectMenuIds(user.getRoleId());
            List<String> menuList = null;
            if (StringUtils.isNotEmpty(menuIds)) {
                menuList = Arrays.asList(menuIds.split(Constants.COMMA));
            }
            user.setMenuList(menuList);
            boolean flag = redisClient.set(LOGIN_TOKEN_KEY + user.getId(), token, EXPIRES_TIME * 60 * 60);
            if (flag) {
                return R.ok(token);
            } else {
                return R.fail("redis服务异常！请重新登录");
            }
        } else {
            return R.fail(322, "账号或密码错误！");
//            throw new CustomException(ResultStatusEnum.ACCOUNT_PASSWORD_INCORRECT);
        }
    }

    @Override
    public R<?> getAdminMenu() {
        User user = getUserInfo();
        AdminInfo adminInfo = adminInfoMapper.selectAdminById(user.getId());
        String menuIds = sysRoleMapper.selectMenuIds(adminInfo.getRoleId());
        if (StringUtils.isEmpty(menuIds)) {
            return R.ok(null);
        }
        List<String> menuIdsList = null;
        try {
            menuIdsList = Arrays.asList(menuIds.split(Constants.COMMA));
        } catch (Exception e) {
            e.printStackTrace();
            return R.ok(new ArrayList<>());
        }

        //先获取全部的菜单
        List<SysMenu> rootMenu = sysMenuMapper.selectMenus(menuIdsList);

        // 最后的结果
        List<SysMenu> menuList = new ArrayList<>();
        // 先找到所有的一级菜单
        for (SysMenu menu : rootMenu) {
            // 一级菜单没有parentId
            if (menu.getParentId() == null) {
                menuList.add(menu);
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysMenu menu : menuList) {
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }
        return R.ok(menuList);
    }

    @Override
    public R<?> getVerifyCode() throws CustomException {
        VerifyCodeUtils code = new VerifyCodeUtils();
        BufferedImage image = code.getImage();
        String text = code.getText().toLowerCase();
        String uuid = UUID.randomUUID().toString();
        log.info("verify_code:{}", text);
        //需要存入redis 这里暂不做处理
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            Map<String, Object> res = new HashMap<>();
            VerifyCodeUtils.output(image, stream);
            res.put("uuid", uuid);
            //md5加密
            res.put("text", MD5Util.getMD5(text));
            res.put("img", Base64.encode(stream.toByteArray()));
            return R.ok(res);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(500, "系统繁忙，请稍后重试！");
//            throw new CustomException(ResultStatusEnum.SYSTEM_ABNORMAL);
        }
    }

    @Override
    public R<User> getAdminInfo() {
        User user = getUserInfo();
        return R.ok(user);
    }

    @Override
    public R<?> logout() {
        String token = ServletUtils.getToken();
        //删除用户缓存信息
        redisClient.del(LOGIN_TOKEN_KEY + token);
        log.info("退出登录");
        return R.ok();
    }

    @Override
    public R<?> editAdminPass(String oldPassword, String newPassword) {
        User user = getUserInfo();
        // 先校验原始密码是否正确
        AdminInfo adminInfo = adminInfoMapper.selectById(user.getId());
        if (adminInfo.getPassword().equals(MD5Util.getMD5(oldPassword))) {
            adminInfoMapper.updatePassById(MD5Util.getMD5(newPassword), adminInfo.getId());
        } else {
            return R.fail(500, "原始密码不正确！");
        }
        return R.ok();
    }

    @Override
    public R<?> updateSelfInfo(AdminInfo adminInfo) {
        User user = getUserInfo();
        //修改
        adminInfo.setUpdater(user.getId());
        adminInfo.setUpdateTime(new Date());
        try {
            adminInfoMapper.updateById(adminInfo);
        } catch (Exception e) {
            return R.fail(341, "用户名或者用户邮箱已经被注册!");
        }

        //更新缓存
        BeanUtils.copyProperties(adminInfo, user);
        String token = ServletUtils.getToken();
        long expire = redisClient.getExpire(LOGIN_TOKEN_KEY + token);
        redisClient.set(LOGIN_TOKEN_KEY + token, user, expire);
        return R.okMsg("保存成功！");
    }

//    /**
//     * 找回密码功能,先生成一个6位数字字符串,然后将用户id和password组合作为key,
//     * 存入Redis中,设置过期时间.
//     *
//     * @param toMail
//     * @return
////     */
//    @Override
//    public R<?> sendMail(String toMail) {
//        if (toMail == null) {
//            return R.fail("邮件不能为空");
//        }
//        SimpleMailMessage message = new SimpleMailMessage();
//        LambdaQueryWrapper<AdminInfo> lambdaQueryWrapper = Wrappers.lambdaQuery();
//        lambdaQueryWrapper.eq(AdminInfo::getMail, toMail);
//        AdminInfo adminInfo = adminInfoMapper.selectOne(lambdaQueryWrapper);
//        if (adminInfo != null && adminInfo.getId() != null) {
//            if (!"2".equals(adminInfo.getStatus())) {
//                return R.fail("该账号已被删除或禁用");
//            }
//            //邮件设置
//            String code = StringUtils.getRandomCode();
//            emailService.sendVerifyCode(toMail, code);
//            String redisKey = toMail + "_password";
//            redisClient.set(redisKey, code, 600);
//            return R.ok("发送邮件成功");
//        } else {
//            return R.fail("该邮箱未绑定账号。");
//        }
//    }

//    /**
//     * 检查忘记密码时用户发送的验证码是否正确
//     *
//     * @param code 用户填写的验证码
//     * @return
//     */
//    @Override
//    public R<?> checkVerificationCode(String code, String toMail) {
//        String redisKey = toMail + "_password";
//        String redisValue = String.valueOf(redisClient.get(redisKey));
//        if (redisValue.isEmpty()) {
//            return R.fail(VERIFICATION_CODE_EXPIRED);
//        } else if (code.equals(redisValue)) {
//            LambdaQueryWrapper<AdminInfo> lambdaQueryWrapper = Wrappers.lambdaQuery();
//            lambdaQueryWrapper
//                    .eq(toMail != null, AdminInfo::getMail, toMail);
//            AdminInfo adminInfo = adminInfoMapper.selectOne(lambdaQueryWrapper);
//
//            R<?> res = adminInfoService.resetPass(adminInfo.getId());
//            if (res.getCode() == 200) {
//                return R.ok("验证成功");
//            } else {
//                return R.fail("重置密码失败,请稍后重试或联系管理员处理");
//            }
//        } else {
//            return R.fail(VERIFICATION_CODE_FAILURE);
//        }
//    }

    @Override
    public String selectNameByUserId(String userId) {
        String userName = adminInfoMapper.selectNameByUserId(userId);
        return userName;
    }

    @Override
    public String selectRoleIdByUserId(String userId) {
        String roleId = adminInfoMapper.selectRoleIdByUserId(userId);
        return roleId;
    }

    @Override
    public List<AdminNameResult> selectNameByUserIds(Set<String> assignees) {
        List<AdminNameResult> startUserNames = adminInfoMapper.selectNameByUserIds(assignees);
        return startUserNames;
    }

    @Override
    public List<User> getUserByType(String type) {
        return null;
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

    /**
     * 获取当前登录用户信息
     */
    public User getUserInfo() {
        Integer id = securityService.getLoginUserId();
        AdminInfo adminInfo = adminInfoMapper.selectById(id);
        User user = new User();
        BeanUtils.copyProperties(adminInfo, user);
        String menuIds = sysRoleMapper.selectMenuIds(adminInfo.getRoleId());
        List<String> menuIdsList = null;
        if (StringUtils.isNotEmpty(menuIds)) {
            menuIdsList = Arrays.asList(menuIds.split(Constants.COMMA));
        }
        if (menuIdsList != null && menuIdsList.size() != 0) {
            List<SysMenu> sysMenus = sysMenuMapper.selectMenus(menuIdsList);
            List<String> sysMenuNameList = sysMenus.stream().map(SysMenu::getName).collect(Collectors.toList());
            user.setSysMenuNameList(sysMenuNameList);
        }
        user.setMenuList(menuIdsList);
        // 将登录系统信息加入返回信息中
//        user.setUnitId(adminInfo.getUnitId());
        return user;
    }

    /**
     * 获取（Integer）元素在数组中的索引
     *
     * @param nodes
     * @param key
     * @return
     */
    int getArrIndex(Integer[] nodes, Integer key) {
        for (int i = 0; i < nodes.length; i++) {
            if (key.equals(nodes[i])) {
                return i;
            }
        }
        return -1;
    }

}
