package com.zzy.jpaCrud.single_table.model.dto;

import com.zzy.jpaCrud.common.model.dto.BaseQueryDTO;
import lombok.Data;

/**
 * @Classname JpaUserQueryDTO
 * @Description TODO
 * @Date 2020/6/22 11:50
 * @Created by Zzy
 */
@Data
public class JpaUserQueryDTO extends BaseQueryDTO {

    private Long id;

    private String userName;

    private String passWord;

    private Integer age;

}
