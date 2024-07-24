package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.manager.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    String selectMenuIds(@Param("roleId") Integer roleId);

    List<Map<String, Object>> selectOption();

    Integer selectRoleId(Integer roleId);

}
