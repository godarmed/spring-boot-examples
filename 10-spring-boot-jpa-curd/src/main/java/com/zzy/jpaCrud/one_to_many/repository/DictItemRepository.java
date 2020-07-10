package com.zzy.jpaCrud.one_to_many.repository;

import com.zzy.jpaCrud.common.repository.BaseRepository;
import com.zzy.jpaCrud.one_to_many.entity.DictItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;

/**
 * @Classname DictItem
 * @Description TODO
 * @Date 2020/7/6 15:31
 * @Created by Zzy
 */
public interface DictItemRepository  extends BaseRepository<DictItem, Long> {

    DictItem findDictItemById(Long id);

    @EntityGraph(value = "DictItem.all",type = EntityGraph.EntityGraphType.FETCH)
    @Override
    Page<DictItem> findAll(Specification<DictItem> specification, Pageable pageable);
}
