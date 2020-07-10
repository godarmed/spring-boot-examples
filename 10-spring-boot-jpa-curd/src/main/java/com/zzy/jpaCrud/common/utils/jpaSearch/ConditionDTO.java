package com.zzy.jpaCrud.common.utils.jpaSearch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import java.io.Serializable;

/**
 * @Classname ConditionDTO
 * @Description TODO
 * @Date 2020/6/22 10:42
 * @Created by Zzy
 */

@ApiModel(value = "拼接查询条件的入参")
@Data
public class ConditionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public ConditionDTO(From<?, ?> root, CriteriaBuilder builder, String fieldName, String aliasName, Class<?> fieldType, Object fieldValue) {
        this.root = root;
        this.builder = builder;
        this.fieldName = fieldName;
        this.aliasName = aliasName;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
    }

    private From<?, ?> root;

    private CriteriaBuilder builder;

    @ApiModelProperty(value = "字段名称", required = true)
    private String fieldName;

    @ApiModelProperty(value = "字段别名", notes = "传入值不为null时，作为表字段处理", required = false)
    private String aliasName;

    @ApiModelProperty(value = "字段值类型", required = true)
    private Class<?> fieldType;

    @ApiModelProperty(value = "字段值类型", required = true)
    private Object fieldValue;

}
