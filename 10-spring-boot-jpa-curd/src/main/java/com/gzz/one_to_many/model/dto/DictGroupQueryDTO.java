package com.gzz.one_to_many.model.dto;

import com.gzz.common.model.dto.BaseQueryDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @Classname DictGroupQueryDTO
 * @Description TODO
 * @Date 2020/7/6 16:03
 * @Created by Zzy
 */
@Data
public class DictGroupQueryDTO extends BaseQueryDTO {

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


    @ApiModelProperty("字典项key")
    private String dictItemkey;

    @Column(name = "value", length = 4096)
    @ApiModelProperty("字典项value")
    private String dictItemValue;

    @ApiModelProperty("字典项状态")
    private String dictItemStatus;
}
