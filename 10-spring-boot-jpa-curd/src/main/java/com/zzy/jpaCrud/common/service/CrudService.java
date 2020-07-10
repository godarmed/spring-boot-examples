package com.zzy.jpaCrud.common.service;

import com.zzy.jpaCrud.common.model.dto.BaseQueryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Classname CrudService
 * @Description TODO
 * @Date 2020/6/21 9:49
 * @Created by Zzy
 */
public interface CrudService<T,ID,Q extends BaseQueryDTO> {

    T add(T model);

    T modify(T model);

    ID delete(ID id);

    List<ID> batchDelete(List<ID> id);

    Page<T> findAllByPage(Q query);

}
