package com.gzz.single_table.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname JpaUserVO
 * @Description TODO
 * @Date 2020/6/22 16:55
 * @Created by Zzy
 */

@Data
public class JpaUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String passWord;

    private Integer age;

}
