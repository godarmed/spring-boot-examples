package com.zzy.jpaCrud.one_to_many.model.dto;

import lombok.Data;

import java.util.Map;

/**
 * @Classname TestDTO
 * @Description TODO
 * @Date 2020/7/8 10:39
 * @Created by Zzy
 */
@Data
public class TestDTO {

    DictItemDTO dictItemDTO;

    Map<String, Object> dictItemMapDTO;

}
