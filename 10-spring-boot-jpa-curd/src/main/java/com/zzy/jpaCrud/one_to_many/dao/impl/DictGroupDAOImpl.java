package com.zzy.jpaCrud.one_to_many.dao.impl;

import com.zzy.jpaCrud.common.repository.BaseRepository;
import com.zzy.jpaCrud.one_to_many.dao.DictGroupDAO;
import com.zzy.jpaCrud.one_to_many.entity.DictGroup;
import com.zzy.jpaCrud.one_to_many.repository.DictGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname DictGroupDAOImpl
 * @Description TODO
 * @Date 2020/7/6 15:37
 * @Created by Zzy
 */
@Service
public class DictGroupDAOImpl implements DictGroupDAO {

    @Autowired
    DictGroupRepository repository;

    @Override
    public BaseRepository<DictGroup, Long> getRepository() {
        return repository;
    }

    @Override
    public DictGroup findDictGroupById(Long id) {
        return repository.findDictGroupById(id);
    }
}
