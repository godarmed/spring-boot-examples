package com.zzy.jpaCrud.one_to_many.servcie.impl;


import com.zzy.jpaCrud.common.model.JoinField;
import com.zzy.jpaCrud.common.utils.jpaSearch.ResourcesPredicate;
import com.zzy.jpaCrud.one_to_many.dao.DictGroupDAO;
import com.zzy.jpaCrud.one_to_many.dao.DictItemDAO;
import com.zzy.jpaCrud.one_to_many.entity.DictGroup;
import com.zzy.jpaCrud.one_to_many.entity.DictItem;
import com.zzy.jpaCrud.one_to_many.model.dto.DictGroupDTO;
import com.zzy.jpaCrud.one_to_many.model.dto.DictGroupQueryDTO;
import com.zzy.jpaCrud.one_to_many.model.dto.DictItemDTO;
import com.zzy.jpaCrud.one_to_many.model.dto.DictItemQueryDTO;
import com.zzy.jpaCrud.one_to_many.model.vo.DictGroupVO;
import com.zzy.jpaCrud.one_to_many.model.vo.DictItemVO;
import com.zzy.jpaCrud.one_to_many.servcie.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Classname DictServiceImpl
 * @Description TODO
 * @Date 2020/7/6 15:52
 * @Created by Zzy
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    DictGroupDAO dictGroupDAO;

    @Autowired
    DictItemDAO dictItemDAO;

    @Override
    public DictGroupVO addDictGroup(DictGroupDTO request) {
        DictGroup dictGroup = new DictGroup();
        BeanUtils.copyProperties(request, dictGroup);
        dictGroup = dictGroupDAO.save(dictGroup);
        DictGroupVO dictGroupVO = new DictGroupVO();
        BeanUtils.copyProperties(dictGroup, dictGroupVO);
        return dictGroupVO;
    }

    @Override
    public DictGroupVO updateDictGroup(DictGroupDTO request) {
        DictGroup dictGroup = null;
        if((dictGroup = dictGroupDAO.findDictGroupById(request.getId())) == null){
            throw new RuntimeException("要修改的字典组不存在!");
        }
        BeanUtils.copyProperties(request, dictGroup);
        dictGroup = dictGroupDAO.save(dictGroup);
        DictGroupVO dictGroupVO = new DictGroupVO();
        BeanUtils.copyProperties(dictGroup, dictGroupVO);
        return dictGroupVO;
    }

    @Override
    public Page<DictGroup> findDictGroupByPage(DictGroupQueryDTO request) {
        generateDictGroupQuery(request);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC,"id"));
        return dictGroupDAO.findAll(new ResourcesPredicate<DictGroup, DictGroupQueryDTO>(request), pageable);
    }

    private void generateDictGroupQuery(DictGroupQueryDTO request) {
        List<JoinField> joinFields = new ArrayList<>();
        HashMap<String, JoinField> entityHashMap = new HashMap<>();
        entityHashMap.put("dictItemkey", new JoinField() {
            {
                setFieldName("dictItemkey");
                setAliasName("key");
                setTableName("dictItems");
                setFieldValue(request.getDictItemkey());
                setFieldType(String.class);
            }
        });
        if (!StringUtils.isEmpty(request.getDictItemkey())) {
            joinFields.add(entityHashMap.get("dictItemkey"));
        }
        request.setJoinFieldList(joinFields);
    }

    @Override
    public void delDictGroup(Long id) {
        dictGroupDAO.deleteById(id);
    }


    @Override
    public DictItemVO addDictItem(DictItemDTO request) {
        DictItem dictItem = new DictItem();
        BeanUtils.copyProperties(request, dictItem);
        //查询字典组
        DictGroup dictGroup = dictGroupDAO.findDictGroupById(request.getDictGroupId());
        if(dictGroup == null){
            throw new RuntimeException("要加入的字典组不存在!");
        }
        dictItem.setDictGroup(dictGroup);
        dictItem = dictItemDAO.save(dictItem);
        DictItemVO dictItemVO = new DictItemVO();
        BeanUtils.copyProperties(dictItem,dictItemVO);
        dictItemVO.setDictGroupId(dictItem.getDictGroup().getId());
        return dictItemVO;
    }

    @Override
    public DictItemVO updateDictItem(DictItemDTO request) {
        //查询字典项
        DictItem dictItem = dictItemDAO.findDictItemById(request.getId());
        if(dictItem == null){
            throw new RuntimeException("要修改的字典项不存在!");
        }
        //查询字典组
        DictGroup dictGroup = dictGroupDAO.findDictGroupById(request.getDictGroupId());
        if(dictGroup == null){
            throw new RuntimeException("要加入的字典组不存在!");
        }
        BeanUtils.copyProperties(request, dictItem);
        dictItem.setDictGroup(dictGroup);
        dictItem = dictItemDAO.save(dictItem);
        DictItemVO dictItemVO = new DictItemVO();
        BeanUtils.copyProperties(dictItem,dictItemVO);
        dictItemVO.setDictGroupId(dictItem.getDictGroup().getId());
        return dictItemVO;
    }

    @Override
    public Page<DictItem> findDictItemByPage(DictItemQueryDTO request) {
        generateDictItemQuery(request);
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC,"id"));
        return dictItemDAO.findAll(new ResourcesPredicate<DictItem, DictItemQueryDTO>(request), pageable);
    }

    private void generateDictItemQuery(DictItemQueryDTO request) {
        List<JoinField> joinFields = new ArrayList<>();
        HashMap<String, JoinField> entityHashMap = new HashMap<>();
        entityHashMap.put("dictGroupId", new JoinField() {
            {
                setFieldName("dictGroupId");
                setAliasName("id");
                setTableName("dictGroup");
                setFieldValue(request.getDictGroupId());
                setFieldType(Long.class);
            }
        });
        entityHashMap.put("dictCnName", new JoinField() {
            {
                setFieldName("dictCnName");
                setAliasName("cnName");
                setTableName("dictGroup");
                setFieldValue(request.getDictCnName());
                setFieldType(String.class);
            }
        });
        if (request.getDictGroupId() != null) {
            joinFields.add(entityHashMap.get("dictGroupId"));
        }
        if (!StringUtils.isEmpty(request.getDictCnName())) {
            joinFields.add(entityHashMap.get("dictCnName"));
        }
        request.setJoinFieldList(joinFields);
    }

    @Override
    public void delDictItem(Long id) {
        dictItemDAO.deleteById(id);
    }
}
