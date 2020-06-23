package com.gzz.single_table.service;

import com.gzz.single_table.entity.JpaUser;
import com.gzz.single_table.model.dto.JpaUserDTO;
import com.gzz.single_table.model.dto.JpaUserQueryDTO;
import com.gzz.single_table.model.vo.JpaUserVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JpaUserService {

    JpaUserVO findJpaUserById(Long id);

    JpaUserVO save(JpaUserDTO user);

    JpaUserVO edit(JpaUserDTO user);

    void delete(Long id);

    Page<JpaUser> findAllByPage(JpaUserQueryDTO request);

}
