package com.zzy.jpaCrud.one_to_many.dao;

import com.zzy.jpaCrud.common.dao.BaseDAO;
import com.zzy.jpaCrud.one_to_many.entity.DictGroup;

/**
 * @Classname DictGroupDAO
 * @Description TODO
 * @Date 2020/7/6 15:36
 * @Created by Zzy
 */
public interface DictGroupDAO extends BaseDAO<DictGroup, Long> {

    DictGroup findDictGroupById(Long id);

}
