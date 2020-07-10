package com.zzy.jpaCrud.single_table.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname JpaUserDTO
 * @Description TODO
 * @Date 2020/6/22 16:55
 * @Created by Zzy
 */

@Data
public class JpaUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String passWord;

    private Integer age;

}
