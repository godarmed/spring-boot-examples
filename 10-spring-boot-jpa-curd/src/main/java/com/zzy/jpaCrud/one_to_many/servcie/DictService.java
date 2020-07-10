package com.zzy.jpaCrud.one_to_many.servcie;

import com.zzy.jpaCrud.one_to_many.entity.DictGroup;
import com.zzy.jpaCrud.one_to_many.entity.DictItem;
import com.zzy.jpaCrud.one_to_many.model.dto.DictGroupDTO;
import com.zzy.jpaCrud.one_to_many.model.dto.DictGroupQueryDTO;
import com.zzy.jpaCrud.one_to_many.model.dto.DictItemDTO;
import com.zzy.jpaCrud.one_to_many.model.dto.DictItemQueryDTO;
import com.zzy.jpaCrud.one_to_many.model.vo.DictGroupVO;
import com.zzy.jpaCrud.one_to_many.model.vo.DictItemVO;
import org.springframework.data.domain.Page;

/**
 * @Classname DictService
 * @Description TODO
 * @Date 2020/7/6 15:43
 * @Created by Zzy
 */
public interface DictService {
    //创建字典组
    DictGroupVO addDictGroup(DictGroupDTO request);
    //修改字典组
    DictGroupVO updateDictGroup(DictGroupDTO request);
    //查询字典组
    Page<DictGroup> findDictGroupByPage(DictGroupQueryDTO request);
    //删除字典组
    void delDictGroup(Long id);

    //创建字典项
    DictItemVO addDictItem(DictItemDTO request);
    //修改字典组
    DictItemVO updateDictItem(DictItemDTO request);
    //查询字典项
    Page<DictItem> findDictItemByPage(DictItemQueryDTO request);
    //删除字典项
    void delDictItem(Long id);
}
