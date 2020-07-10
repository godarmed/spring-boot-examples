package com.zzy.jpaCrud.single_table.service;

import com.zzy.jpaCrud.single_table.entity.JpaUser;
import com.zzy.jpaCrud.single_table.model.dto.JpaUserDTO;
import com.zzy.jpaCrud.single_table.model.dto.JpaUserQueryDTO;
import com.zzy.jpaCrud.single_table.model.vo.JpaUserVO;
import org.springframework.data.domain.Page;

public interface JpaUserService {

    JpaUserVO findJpaUserById(Long id);

    JpaUserVO save(JpaUserDTO user);

    JpaUserVO edit(JpaUserDTO user);

    void delete(Long id);

    Page<JpaUser> findAllByPage(JpaUserQueryDTO request);

}
