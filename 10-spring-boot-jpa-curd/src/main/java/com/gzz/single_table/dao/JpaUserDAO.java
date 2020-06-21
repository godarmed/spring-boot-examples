package com.gzz.single_table.dao;

import com.gzz.common.dao.BaseDAO;
import com.gzz.single_table.entity.JpaUser;

/**
 * @Classname JpaUserDAO
 * @Description TODO
 * @Date 2020/6/21 9:39
 * @Created by Zzy
 */
public interface JpaUserDAO extends BaseDAO<JpaUser, Long> {

    JpaUser findJpaUserById(Long id);

}
