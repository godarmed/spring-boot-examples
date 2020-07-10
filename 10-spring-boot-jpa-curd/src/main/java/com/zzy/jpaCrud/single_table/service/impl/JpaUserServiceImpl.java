package com.zzy.jpaCrud.single_table.service.impl;

import com.zzy.jpaCrud.common.utils.jpaSearch.ResourcesPredicate;
import com.zzy.jpaCrud.single_table.dao.JpaUserDAO;
import com.zzy.jpaCrud.single_table.entity.JpaUser;
import com.zzy.jpaCrud.single_table.model.dto.JpaUserDTO;
import com.zzy.jpaCrud.single_table.model.dto.JpaUserQueryDTO;
import com.zzy.jpaCrud.single_table.model.vo.JpaUserVO;
import com.zzy.jpaCrud.single_table.service.JpaUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class JpaUserServiceImpl implements JpaUserService {

    @Autowired
    private JpaUserDAO jpaUserDAO;

    @Override
    public JpaUserVO findJpaUserById(Long id) {
        JpaUser jpaUser = jpaUserDAO.findJpaUserById(id);
        JpaUserVO jpaUserVO = new JpaUserVO();
        BeanUtils.copyProperties(jpaUser, jpaUserVO);
        return jpaUserVO;
    }

    @Override
    public JpaUserVO save(JpaUserDTO request) {
        JpaUser jpaUser = new JpaUser();
        BeanUtils.copyProperties(request, jpaUser);
        jpaUser = jpaUserDAO.save(jpaUser);
        JpaUserVO jpaUserVO = new JpaUserVO();
        BeanUtils.copyProperties(jpaUser, jpaUserVO);
        return jpaUserVO;
    }

    @Override
    public JpaUserVO edit(JpaUserDTO request) {
        JpaUser jpaUser = null;
        if((jpaUser = jpaUserDAO.findJpaUserById(request.getId())) == null){
            throw new RuntimeException("要修改的用户不存在!");
        }
        BeanUtils.copyProperties(request, jpaUser);
        jpaUser = jpaUserDAO.save(jpaUser);
        JpaUserVO jpaUserVO = new JpaUserVO();
        BeanUtils.copyProperties(jpaUser, jpaUserVO);
        return jpaUserVO;
    }

    @Override
    public void delete(Long id) {
        jpaUserDAO.deleteById(id);
    }

    @Override
    public Page<JpaUser> findAllByPage(JpaUserQueryDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "id"));
        return jpaUserDAO.findAll(new ResourcesPredicate<JpaUser, JpaUserQueryDTO>(request), pageable);

    }
}


