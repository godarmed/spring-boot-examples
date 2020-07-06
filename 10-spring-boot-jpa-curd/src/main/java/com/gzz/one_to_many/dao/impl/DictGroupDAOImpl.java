package com.gzz.one_to_many.dao.impl;

import com.gzz.common.repository.BaseRepository;
import com.gzz.one_to_many.dao.DictGroupDAO;
import com.gzz.one_to_many.entity.DictGroup;
import com.gzz.one_to_many.repository.DictGroupRepository;
import com.gzz.single_table.entity.JpaUser;
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
