package com.zzy.jpaCrud.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname JoinField
 * @Description TODO
 * @Date 2020/6/21 10:48
 * @Created by Zzy
 */

@ApiModel(value = "连表参数")
@Data
public class JoinField implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字段名称", required = true)
    private String fieldName;

    @ApiModelProperty(value = "字段别名", notes = "传入值不为null时，作为表字段处理", required = false)
    private String aliasName;

    @ApiModelProperty(value = "字段值", required = true)
    private Object fieldValue;

    @ApiModelProperty(value = "字段值类型", required = true)
    private Class<?> fieldType;

    @ApiModelProperty(value = "字段所属表表名", notes = "使用时表与表之间必须在jpa上有关联", required = true)
    private String tableName;

    @ApiModelProperty(value = "字段所在表与查询的表的连接关系",notes = "0-[一对一], 1-[一对多], 2-[多对多]", required = true)
    private Integer joinType = 0;

}
