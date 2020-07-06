package com.gzz.one_to_many.dao.impl;

import com.gzz.common.repository.BaseRepository;
import com.gzz.one_to_many.dao.DictItemDAO;
import com.gzz.one_to_many.entity.DictItem;
import com.gzz.one_to_many.repository.DictItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname DictItemDAOImpl
 * @Description TODO
 * @Date 2020/7/6 15:41
 * @Created by Zzy
 */
@Service
public class DictItemDAOImpl implements DictItemDAO {

    @Autowired
    DictItemRepository repository;

    @Override
    public BaseRepository<DictItem, Long> getRepository() {
        return repository;
    }

    @Override
    public DictItem findDictItemById(Long id) {
        return repository.findDictItemById(id);
    }
}
