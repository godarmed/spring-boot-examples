package com.gzz.single_table.repository;

import com.gzz.common.repository.BaseRepository;
import com.gzz.single_table.entity.JpaUser;
import org.springframework.stereotype.Repository;

/**
 * @Classname Repository
 * @Description TODO
 * @Date 2020/6/21 9:24
 * @Created by Zzy
 */

@Repository
public interface JpaUserRepository extends BaseRepository<JpaUser, Long> {

    JpaUser findJpaUserById(Long id);

}