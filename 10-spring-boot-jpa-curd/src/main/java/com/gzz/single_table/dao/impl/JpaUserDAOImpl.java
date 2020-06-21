package com.gzz.single_table.dao.impl;

import com.gzz.common.repository.BaseRepository;
import com.gzz.single_table.dao.JpaUserDAO;
import com.gzz.single_table.entity.JpaUser;
import com.gzz.single_table.repository.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname JpaUserDAOImpl
 * @Description TODO
 * @Date 2020/6/21 9:40
 * @Created by Zzy
 */

@Service
public class JpaUserDAOImpl implements JpaUserDAO{

    @Autowired
    JpaUserRepository repository;

    @Override
    public BaseRepository<JpaUser, Long> getRepository() {
        return repository;
    }

    @Override
    public JpaUser findJpaUserById(Long id) {
        return repository.findJpaUserById(id);
    }
}
