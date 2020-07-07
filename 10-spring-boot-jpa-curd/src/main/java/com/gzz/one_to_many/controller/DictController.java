package com.gzz.one_to_many.controller;

import com.gzz.common.model.vo.MsgPageInfo;
import com.gzz.common.model.vo.ResultModel;
import com.gzz.one_to_many.dao.DictGroupDAO;
import com.gzz.one_to_many.entity.DictGroup;
import com.gzz.one_to_many.entity.DictItem;
import com.gzz.one_to_many.model.dto.DictGroupQueryDTO;
import com.gzz.one_to_many.model.dto.DictItemQueryDTO;
import com.gzz.one_to_many.model.vo.DictGroupVO;
import com.gzz.one_to_many.model.vo.DictItemVO;
import com.gzz.one_to_many.repository.DictGroupRepository;
import com.gzz.one_to_many.servcie.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
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

    @RequestMapping(PATH + "/queryDictItemByPage")
    public ResultModel<List<DictItemVO>> query(@RequestBody DictItemQueryDTO request) {
        ResultModel<List<DictItemVO>> ret = new ResultModel<>();
        Page<DictItem> dictItemPage =  dictService.findDictItemByPage(request);
        List<DictItemVO> dictItemList = dictItemPage.stream().map(item->{
            DictItemVO dictItemVO = new DictItemVO();
            BeanUtils.copyProperties(item, dictItemVO);
            if(item.getDictGroup() != null){
                dictItemVO.setDictGroupId(item.getDictGroup().getId());
            }
            return dictItemVO;
        }).collect(Collectors.toList());
        ret.setData(dictItemList, MsgPageInfo.loadFromPageable(dictItemPage));
        return ret;
    }

    @Autowired
    DictGroupRepository dictGroupRepository;

    @RequestMapping(PATH + "/test")
    public Object queryTest(@RequestBody DictItemQueryDTO request) {
        return dictGroupRepository.findAllById(Arrays.asList(15L,16L));
    }

    public static void main(String[] args) {
        List<String> source = new ArrayList<>();
        source.add("a");
        source.add("b");
        List<String> source2 = Arrays.asList("c","d");
        source.addAll(source2);
        System.out.println(source);
    }
}
