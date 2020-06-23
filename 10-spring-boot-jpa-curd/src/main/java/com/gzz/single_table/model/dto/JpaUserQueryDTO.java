package com.gzz.single_table.model.dto;

import com.gzz.common.model.dto.BaseQueryDTO;
import lombok.Data;

import javax.persistence.Column;

/**
 * @Classname JpaUserQueryDTO
 * @Description TODO
 * @Date 2020/6/22 11:50
 * @Created by Zzy
 */
@Data
public class JpaUserQueryDTO extends BaseQueryDTO{

    private Long id;

    private String userName;

    private String passWord;

    private Integer age;

}
