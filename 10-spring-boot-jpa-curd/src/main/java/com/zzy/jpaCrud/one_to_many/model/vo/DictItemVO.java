package com.zzy.jpaCrud.one_to_many.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname DictItemVO
 * @Description TODO
 * @Date 2020/7/6 15:47
 * @Created by Zzy
 */
@Data
public class DictItemVO implements Serializable {

    private static final long serialVersionUID = 8050660443876463036L;

    @ApiModelProperty("字典项id")
    private Long id;

    @ApiModelProperty("字典项key")
    private String key;

    @ApiModelProperty("字典项value")
    private String value;

    @ApiModelProperty("字典项名称")
    private String name;

    @ApiModelProperty("字典项状态")
    private String status;

    @ApiModelProperty("字典组Id")
    private Long dictGroupId;

}
