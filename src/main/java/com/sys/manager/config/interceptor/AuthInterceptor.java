package com.sys.manager.config.interceptor;

import com.sys.manager.constant.CacheConstants;
import com.sys.manager.constant.Constants;
import com.sys.manager.enums.ResultStatusEnum;
import com.sys.manager.exception.CustomException;
import com.sys.manager.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * @author lichp
 * @version 1.0.0  2020/7/13 14:23
 * @since JDK1.8
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    private String[] whitelist = {"/api/user/admin/login","/api/user/addExpire","/api/user/verifyCode","/api/user/checkExpire"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(Constants.AUTH_TOKEN);

        //检查白名单
        if (checkWhite(request.getRequestURI())) {
            return true;
        }

        log.info("当前用户token:{}", token);
        if (StringUtils.isEmpty(token)) {
            throw new CustomException(ResultStatusEnum.AUTHENTICATION_FAILED);
        }
        // 对token合法性进行验证
        Map<String, String> stringStringMap = JwtUtils.verifyToken(token);
        if (stringStringMap.isEmpty()) {
            throw new CustomException(ResultStatusEnum.AUTHENTICATION_FAILED);
        } else {
            // 获取用户id
            String userId = stringStringMap.get(CacheConstants.DETAILS_USER_ID);
            String userName = stringStringMap.get(CacheConstants.DETAILS_USERNAME);
            log.info("访问用户ID：{},用户名称：{}", userId, userName);
        }
        return true;
    }


    /**
     * 检查白名单
     *
     * @param uri
     * @return
     */
    private Boolean checkWhite(String uri) {
        return Arrays.asList(whitelist).contains(uri);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Do nothing.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Do nothing.
    }
}