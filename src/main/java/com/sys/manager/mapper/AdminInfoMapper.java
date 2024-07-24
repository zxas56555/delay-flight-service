package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sys.manager.entity.AdminInfo;
import com.sys.manager.pojo.AdminInfoResult;
import com.sys.manager.pojo.AdminNameResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
@Repository
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {

    IPage<AdminInfoResult> selectByPageAndCon(IPage<AdminInfoResult> page, @Param("adminInfo") AdminInfo adminInfo);

    String selectByIdCard(String cardId);

    int updatePassById(@Param("password") String password, @Param("id") Integer id);

    AdminInfoResult selectAllConBYId(@Param("id") Integer id);

    String selectNameByUserId(@Param("userId") String userId);

    String selectRoleIdByUserId(@Param("userId") String userId);

    List<AdminNameResult> selectNameByUserIds(@Param("assignees") Set<String> assignees);

    AdminInfo selectAdminById(@Param("id") Integer id);

    AdminInfo selectExist(@Param("name") String name);

    Integer selectIdByName(@Param("name") String name);

    int selectAllCount();

    AdminInfo selectByUserAndPass(@Param("userName") String userName,@Param("password") String password);
}
