package com.gzz.single_table.controller;

import com.gzz.common.model.vo.MsgPageInfo;
import com.gzz.common.model.vo.ResultModel;
import com.gzz.single_table.entity.JpaUser;
import com.gzz.single_table.model.dto.JpaUserDTO;
import com.gzz.single_table.model.dto.JpaUserQueryDTO;
import com.gzz.single_table.model.vo.JpaUserVO;
import com.gzz.single_table.service.JpaUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Classname JpaUserController
 * @Description TODO
 * @Date 2020/6/22 11:51
 * @Created by Zzy
 */
@RestController
public class JpaUserController {

    private final static String PATH = "/jpaUser" ;

    @Autowired
    JpaUserService jpaUserService;

    @RequestMapping(PATH + "/queryByPage")
    public ResultModel<List<JpaUserVO>> query(@RequestBody JpaUserQueryDTO request) {
        ResultModel<List<JpaUserVO>> ret = new ResultModel<>();
        Page<JpaUser> jpaUserPage =  jpaUserService.findAllByPage(request);
        List<JpaUserVO> jpaUserList = jpaUserPage.stream().map(item->{
            JpaUserVO jpaUserVO = new JpaUserVO();
            BeanUtils.copyProperties(item, jpaUserVO);
            return jpaUserVO;
        }).collect(Collectors.toList());
        ret.setData(jpaUserList, MsgPageInfo.loadFromPageable(jpaUserPage));
        return ret;
    }

    @RequestMapping(PATH + "/add")
    public ResultModel<JpaUserVO> add(@RequestBody JpaUserDTO request) {
        ResultModel<JpaUserVO> ret = new ResultModel<>();
        JpaUserVO jpaUserVO = jpaUserService.save(request);
        ret.setData(jpaUserVO);
        return ret;
    }

    @RequestMapping(PATH + "/update")
    public ResultModel<JpaUserVO> update(@RequestBody JpaUserDTO request) {
        ResultModel<JpaUserVO> ret = new ResultModel<>();
        JpaUserVO jpaUserVO = jpaUserService.edit(request);
        ret.setData(jpaUserVO);
        return ret;
    }

    @RequestMapping(PATH + "/delete")
    public void delete(@RequestBody JpaUserDTO request) { jpaUserService.delete(request.getId());
    }

}
