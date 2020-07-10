package com.zzy.jpaCrud.one_to_many.dao;

import com.zzy.jpaCrud.common.dao.BaseDAO;
import com.zzy.jpaCrud.one_to_many.entity.DictItem;

/**
 * @Classname DictItemDAO
 * @Description TODO
 * @Date 2020/7/6 15:40
 * @Created by Zzy
 */
public interface DictItemDAO extends BaseDAO<DictItem, Long> {

    DictItem findDictItemById(Long id);

}
