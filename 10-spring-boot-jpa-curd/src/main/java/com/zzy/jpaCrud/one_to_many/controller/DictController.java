package com.zzy.jpaCrud.one_to_many.controller;


import com.zzy.jpaCrud.common.model.vo.MsgPageInfo;
import com.zzy.jpaCrud.common.model.vo.ResultModel;
import com.zzy.jpaCrud.one_to_many.entity.DictGroup;
import com.zzy.jpaCrud.one_to_many.entity.DictItem;
import com.zzy.jpaCrud.one_to_many.model.dto.DictGroupQueryDTO;
import com.zzy.jpaCrud.one_to_many.model.dto.DictItemQueryDTO;
import com.zzy.jpaCrud.one_to_many.model.dto.TestDTO;
import com.zzy.jpaCrud.one_to_many.model.vo.DictGroupVO;
import com.zzy.jpaCrud.one_to_many.model.vo.DictItemVO;
import com.zzy.jpaCrud.one_to_many.repository.DictGroupRepository;
import com.zzy.jpaCrud.one_to_many.repository.DictItemRepository;
import com.zzy.jpaCrud.one_to_many.servcie.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;
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

    @Autowired
    DictItemRepository dictItemRepository;

    @RequestMapping(PATH + "/test")
    public Object queryTest(@RequestBody TestDTO request) {
        System.out.println(request.getDictItemMapDTO().size());
        DictItem dictItem = dictItemRepository.findDictItemById(10000L);
        Map<String,Object> extMsg = new HashMap<>();
        extMsg.putAll(request.getDictItemMapDTO());
        for (String fieldName : extMsg.keySet()) {
            setField(dictItem, fieldName, extMsg.get(fieldName));
        }
        dictItemRepository.save(dictItem);
        return request;
    }

    /**
     * 反射给对象的属性赋值
     * @param obj           要赋值的对象
     * @param fieldName     要赋值的属性名
     * @param value         要赋予的属性值
     * @param <T>
     */
    public static <T> void setField(T obj, String fieldName , Object value){
        Field f = null;
        try {
            f = obj.getClass().getDeclaredField(fieldName);
            if(f != null){
                f.setAccessible(true);
                f.set(obj, value);
            }else{

            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
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
