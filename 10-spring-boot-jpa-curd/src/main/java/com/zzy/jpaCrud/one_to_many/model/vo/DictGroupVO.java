package com.zzy.jpaCrud.one_to_many.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname DictGroupVO
 * @Description TODO
 * @Date 2020/7/6 15:47
 * @Created by Zzy
 */
@Data
public class DictGroupVO implements Serializable {

    private static final long serialVersionUID = -711111343823177305L;

    @ApiModelProperty("字典组id")
    private Long id;

    @ApiModelProperty("字典组类型")
    private String type;

    @ApiModelProperty("字典组中文名称")
    private String cnName;

    @ApiModelProperty("字典组英文名称")
    private String enName;

    @ApiModelProperty("字典组状态")
    private String status;

    @ApiModelProperty("是否可编辑")
    private String editable;

    @ApiModelProperty("字典组描述")
    private String desc;

    @ApiModelProperty("字典项")
    private List<DictItemVO> dictItems;
}
