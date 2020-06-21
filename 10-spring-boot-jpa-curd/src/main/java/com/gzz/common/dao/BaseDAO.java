package com.gzz.common.dao;

import com.gzz.common.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * @Classname BaseDAO
 * @Description TODO
 * @Date 2020/6/21 9:30
 * @Created by Zzy
 */
public interface BaseDAO<T,ID> {

    BaseRepository<T,ID> getRepository();


    default List<T> findAll() {
        return getRepository().findAll();
    }


    default List<T> findAll(Sort sort) {
        return getRepository().findAll(sort);
    }


    default List<T> findAllById(Iterable<ID> iterable) {
        return getRepository().findAllById(iterable);
    }


    default <S extends T> List<S>  saveAll(Iterable<S> entities) {
        return getRepository().saveAll(entities);
    }


    default void flush() {
        getRepository().flush();
    }


    default <S extends T> S saveAndFlush(S entity) {
        return getRepository().saveAndFlush(entity);
    }


    default void deleteInBatch(Iterable<T> entities) {
        getRepository().deleteInBatch(entities);
    }


    default void deleteAllInBatch() {
        getRepository().deleteAllInBatch();
    }


    default T getOne(ID aLong) {
        return getRepository().getOne(aLong);
    }


    default List<T> findAll(Specification<T> spec) {
        return getRepository().findAll(spec);
    }


    default Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return getRepository().findAll(spec, pageable);
    }


    default List<T> findAll(Specification<T> spec, Sort sort) {
        return getRepository().findAll(spec, sort);
    }


    default long count(Specification<T> spec) {
        return getRepository().count(spec);
    }


    default <S extends T> S save(S entity) {
        return getRepository().save(entity);
    }


    default Optional<T> findById(ID aLong) {
        return getRepository().findById(aLong);
    }


    default long count() {
        return getRepository().count();
    }


    default void deleteById(ID aLong) {
        getRepository().deleteById(aLong);
    }


    default void delete(T entity) {
        getRepository().delete(entity);
    }


    default void deleteAll(Iterable<? extends T> entities) {
        getRepository().deleteAll(entities);
    }


    default void deleteAll() {
        getRepository().deleteAll();
    }


    default boolean existsById(ID aLong) {
        return getRepository().existsById(aLong);
    }

}
