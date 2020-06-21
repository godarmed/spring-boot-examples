package com.gzz.single_table.service;

import com.gzz.single_table.entity.JpaUser;

import java.util.List;

public interface UserService {

    public List<JpaUser> getUserList();

    public JpaUser findJpaUserById(Long id);

    public void save(JpaUser user);

    public void edit(JpaUser user);

    public void delete(Long id);


}
