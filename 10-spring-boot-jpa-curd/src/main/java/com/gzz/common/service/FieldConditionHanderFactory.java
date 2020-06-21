package com.gzz.common.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Classname FieldHanderFactory
 * @Description TODO
 * @Date 2020/6/21 14:21
 * @Created by Zzy
 */

public class FieldConditionHanderFactory {

    private static List<Class<?>> fieldTypeList = new ArrayList<>();

    static {
        fieldTypeList.add(String.class);
        fieldTypeList.add(Integer.class);
        fieldTypeList.add(Long.class);
    }

    /**
     * 拼接新的查询条件
     * @param predicates
     * @return
     */
    public static List<Predicate> addCondition(List<Predicate> predicates){


        return predicates;
    }

    private Predicate gene(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, String key, String value) {
        return builder.like(root.get(key), "%" + value + "%");
    }

    private Predicate integer(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, String key, Integer value) {
        return builder.equal(root.get(key), value);
    }

    private Predicate longType(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, String key, Long value) {
        return builder.equal(root.get(key), value);
    }


    private Predicate timestampType(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, String key, Timestamp[] values) {
        if (values[0] == null && values[1] != null) {
            return builder.lessThan(root.get(key), values[1]);
        }
        if (values[1] == null && values[0] != null) {
            return builder.greaterThan(root.get(key), values[0]);
        }
        if (values[1] != null && values[0] != null) {
            return builder.between(root.get(key), values[0], values[1]);
        }
        return null;
    }

    private Predicate dateType(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, String key, Date[] values) {
        if (values[0] == null && values[1] != null) {
            return builder.lessThan(root.get(key), values[1]);
        }
        if (values[1] == null && values[0] != null) {
            return builder.greaterThan(root.get(key), values[0]);
        }
        if (values[1] != null && values[0] != null) {
            return builder.between(root.get(key), values[0], values[1]);
        }
        return null;
    }
}
