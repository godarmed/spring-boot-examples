package com.gzz.single_table.service.impl;

import com.gzz.single_table.dao.JpaUserDAO;
import com.gzz.single_table.entity.JpaUser;
import com.gzz.single_table.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private JpaUserDAO jpaUserDAO;

    @Override
    public List<JpaUser> getUserList() {
        return jpaUserDAO.findAll();
    }

    @Override
    public JpaUser findJpaUserById(Long id) {
        return jpaUserDAO.findJpaUserById(id);
    }

    @Override
    public void save(JpaUser user) {
        jpaUserDAO.save(user);
    }

    @Override
    public void edit(JpaUser user) {
        jpaUserDAO.save(user);
    }

    @Override
    public void delete(Long id) {
        jpaUserDAO.deleteById(id);
    }
}


