package com.zzy.jpaCrud.one_to_many.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zzy.jpaCrud.common.model.po.BasePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Classname DictionaryGroup
 * @Description TODO
 * @Date 2020/7/6 15:17
 * @Created by Zzy
 */
@Data
@Entity
@Table(
        name = "sys_dict_group",
        indexes = {
                @Index(name = "sys_dictionary_uni_index_type_enName", columnList = "type,enName", unique = true),
                @Index(name = "sys_dictionary_index_STATUS", columnList = "status")
        }
)
@ApiModel(value = "DictGroup", description = "字典表定义")
public class DictGroup implements BasePO, Serializable {

    private static final long serialVersionUID = -711111343823177305L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "`desc`")
    @ApiModelProperty("字典组描述")
    private String desc;

    @OneToMany(mappedBy = "dictGroup", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties({"dictGroup"})
    @ApiModelProperty("字典项定义")
    private List<DictItem> dictItems;
}