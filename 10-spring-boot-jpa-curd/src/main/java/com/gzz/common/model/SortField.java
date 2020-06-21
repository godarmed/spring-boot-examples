package com.gzz.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname SortRule
 * @Description TODO
 * @Date 2020/6/21 10:19
 * @Created by Zzy
 */

@ApiModel(value = "排序参数")
@Data
public class SortField implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "需排序字段")
    private String fieldName;

    @ApiModelProperty(value = "排序方式",example = "1-正序，2-倒序")
    private int direction;

}