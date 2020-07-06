package com.gzz.one_to_many.repository;

import com.gzz.common.repository.BaseRepository;
import com.gzz.one_to_many.entity.DictGroup;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

/**
 * @Classname DictGroupRepository
 * @Description TODO
 * @Date 2020/7/6 15:30
 * @Created by Zzy
 */
public interface DictGroupRepository extends BaseRepository<DictGroup, Long> {

    DictGroup findDictGroupById(Long id);

}
