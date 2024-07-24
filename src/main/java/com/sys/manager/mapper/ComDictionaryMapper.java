package com.sys.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sys.manager.entity.ComDictionary;
import com.sys.manager.pojo.KeyAndNameObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author qmy
 * @since 2023-01-30
 */
public interface ComDictionaryMapper extends BaseMapper<ComDictionary> {

    /**
     * 更新所有id, pid为 所传参数 的数据状态为0
     * 逻辑删除
     *
     * @param id      字典id
     * @param updater 修改人
     */
    Integer updateStatusByIdAndPid(@Param("id") Integer id,
                                   @Param("updater") Integer updater);

    /**
     * 根据type和code唯一确定一项
     *
     * @return
     */
    ComDictionary queryDicByTypeAndCode(@Param("code") String code,
                                        @Param("type") String type);

    /**
     * 查询出字典所有分类
     */
    List<String> queryAllType();

    List<KeyAndNameObject> queryIndustry(@Param("type") String type);

}
