package com.sys.manager.service;

import com.sys.manager.domain.R;
import com.sys.manager.entity.AdminInfo;
import com.sys.manager.pojo.AdminNameResult;
import com.sys.manager.pojo.User;
import org.springframework.http.HttpRequest;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author yx
 * @version 1.0.0  2020/10/21 16:06
 * @since JDK1.8
 */
public interface UserService {

    R<?> adminLogin(String userName, String password);

//    R<?> checkExpire();

//    R<?> addExpire(String empowerKey);

    R<?> getAdminMenu();

    R<?> getVerifyCode() throws IOException;

    R<User> getAdminInfo();

    R<?> editAdminPass(String oldPassword, String newPassword);

    R<?> logout();

    R<?> updateSelfInfo(AdminInfo adminInfo);

//    R<?> sendMail(String toMail);

//    R<?> checkVerificationCode(String code, String toMail);

    String selectNameByUserId(String userId);

    String selectRoleIdByUserId(String userId);

    List<AdminNameResult> selectNameByUserIds(Set<String> assignees);

    List<User> getUserByType(String type);

}
