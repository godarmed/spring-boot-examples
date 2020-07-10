package com.zzy.jpaCrud.one_to_many.repository;

import com.zzy.jpaCrud.common.repository.BaseRepository;
import com.zzy.jpaCrud.one_to_many.entity.DictGroup;

/**
 * @Classname DictGroupRepository
 * @Description TODO
 * @Date 2020/7/6 15:30
 * @Created by Zzy
 */
public interface DictGroupRepository extends BaseRepository<DictGroup, Long> {

    DictGroup findDictGroupById(Long id);

}
