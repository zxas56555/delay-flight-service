package com.sys.manager.security;

import com.sys.manager.exception.CustomException;
import com.sys.manager.pojo.User;
import com.sys.manager.utils.JwtUtils;
import com.sys.manager.utils.RedisClient;
import com.sys.manager.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.sys.manager.constant.CacheConstants.LOGIN_TOKEN_KEY;
import static com.sys.manager.enums.ResultStatusEnum.AUTHENTICATION_FAILED;

/**
 * 安全服务
 * @author lichp
 * @version 1.0.0  2020/10/27 15:40
 * @since JDK1.8
 */
@Component
public class SecurityService {

    @Autowired
    private RedisClient redisClient;


   /**
    * 获取当前登录用户
    * @return
    * @author  lichp
    * @version 1.0.0  2020/10/28 10:10
    * @since JDK1.8
    */
    public User getLoginUser(){
        String token = ServletUtils.getToken();
        //获取token里存入的用户信息
        Map<String, String> userInfo = com.sys.manager.utils.JwtUtils.verifyToken(token);
        String id = userInfo.get("id");
        String permKeys = userInfo.get("permKeys");
        String redisToken = String.valueOf(redisClient.get(LOGIN_TOKEN_KEY + id));
        if(!token.equals(redisToken)){
            throw new CustomException(AUTHENTICATION_FAILED);
        }
        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setPermKeys(permKeys);
        return user;
    }

    /**
     * 获取当前用户登录ID
     * @return java.lang.Integer
     * @author  lichp
     * @version 1.0.0  2020/10/28 10:10
     * @since JDK1.8
     */
    public Integer getLoginUserId(){
        String token = ServletUtils.getToken();
        //获取token里存入的用户信息
        Map<String, String> userInfo = JwtUtils.verifyToken(token);
        String id = userInfo.get("id");
        String redisToken = String.valueOf(redisClient.get(LOGIN_TOKEN_KEY + id));
        if(!token.equals(redisToken)){
            throw new CustomException(AUTHENTICATION_FAILED);
        }
        return Integer.parseInt(id);
    }

}
