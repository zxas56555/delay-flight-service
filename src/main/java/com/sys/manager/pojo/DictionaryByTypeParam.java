package com.sys.manager.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yx
 * @program limp-server
 * @description
 * @createTime 2021/11/29
 */
@Data
public class DictionaryByTypeParam implements Serializable {

    @ApiModelProperty(value = "根据type类型查询树列表")
    public List<String> list;

//    @ApiModelProperty(value = "是否返回树形结构, 1返回, 0不返回, 默认为1")
//    public Integer isTree = 1;

}
