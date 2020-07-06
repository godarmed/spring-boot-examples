package com.gzz.one_to_many.dao;

import com.gzz.common.dao.BaseDAO;
import com.gzz.one_to_many.entity.DictGroup;
import com.gzz.single_table.entity.JpaUser;

/**
 * @Classname DictGroupDAO
 * @Description TODO
 * @Date 2020/7/6 15:36
 * @Created by Zzy
 */
public interface DictGroupDAO extends BaseDAO<DictGroup, Long> {

    DictGroup findDictGroupById(Long id);

}
