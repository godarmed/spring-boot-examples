package com.zzy.jpaCrud.common.model.dto;

import com.zzy.jpaCrud.common.model.JoinField;
import com.zzy.jpaCrud.common.model.SortField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname BaseQueryDTO
 * @Description TODO
 * @Date 2020/6/21 10:17
 * @Created by Zzy
 */

@ApiModel(value = "通用查询模型")
@Data
public class BaseQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "排序字段集合")
    private List<SortField> sortFieldList = new ArrayList<>();

    @ApiModelProperty(value = "关联其他表的字段集合")
    private List<JoinField> joinFieldList = new ArrayList<>();

    @ApiModelProperty(value = "分页页数")
    private Integer page;

    @ApiModelProperty(value = "每页大小")
    private Integer size;

}
