package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.manager.entity.UnitInfo;
import com.sys.manager.pojo.NameAndList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qmy
 * @since 2023-01-31
 */
public interface UnitInfoMapper extends BaseMapper<UnitInfo> {

    List<UnitInfo> selectChildrenByPid(@Param("pid") Integer pid);

    List<NameAndList> selectIds();

    Integer selectIdByName(@Param("unitName") String unitName);

    List<String> selectUnitName(@Param("unitIdList") List<Integer> unitIdList);

}
