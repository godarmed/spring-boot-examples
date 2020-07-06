package com.gzz.one_to_many.controller;

import com.gzz.common.model.vo.MsgPageInfo;
import com.gzz.common.model.vo.ResultModel;
import com.gzz.one_to_many.entity.DictGroup;
import com.gzz.one_to_many.model.dto.DictGroupQueryDTO;
import com.gzz.one_to_many.model.vo.DictGroupVO;
import com.gzz.one_to_many.servcie.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname DictController
 * @Description TODO
 * @Date 2020/7/6 16:20
 * @Created by Zzy
 */
@RestController
public class DictController {

    private final static String PATH = "/dict" ;

    @Autowired
    DictService dictService;

    @RequestMapping(PATH + "/queryDictGroupByPage")
    public ResultModel<List<DictGroupVO>> query(@RequestBody DictGroupQueryDTO request) {
        ResultModel<List<DictGroupVO>> ret = new ResultModel<>();
        Page<DictGroup> dictGroupPage =  dictService.findDictGroupByPage(request);
        List<DictGroupVO> dictGroupList = dictGroupPage.stream().map(item->{
            DictGroupVO dictGroupVO = new DictGroupVO();
            BeanUtils.copyProperties(item, dictGroupVO);
            return dictGroupVO;
        }).collect(Collectors.toList());
        ret.setData(dictGroupList, MsgPageInfo.loadFromPageable(dictGroupPage));
        return ret;
    }
}
