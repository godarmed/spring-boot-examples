package com.gzz.common.utils.jpaSearch;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname FieldHanderFactory
 * @Description TODO
 * @Date 2020/6/21 14:21
 * @Created by Zzy
 */

public class FieldConditionUtil {

    /**
     * 拼接新的查询条件
     * @param conditionDTO
     * @return
     */
    public static List<Predicate> addCondition(ConditionDTO conditionDTO, List<Predicate> predicates){
        //别名确定
        String key = conditionDTO.getFieldName();
        if(!StringUtils.isEmpty(conditionDTO.getAliasName())){
            key = conditionDTO.getAliasName();
        }
        //字段类型确定
        String fieldName = conditionDTO.getFieldName();
        Class<?> fieldType = conditionDTO.getFieldType();
        Object fieldValue = conditionDTO.getFieldValue();
        From<?, ?> root = conditionDTO.getRoot();
        CriteriaBuilder builder = conditionDTO.getBuilder();

        Predicate predicate = null;
        if (String.class.isAssignableFrom(fieldType) && fieldValue != null && !"".equals(fieldValue)) {
            predicate = generateCondition(root, builder, fieldName, String.valueOf(fieldValue));
        }
        if (Integer.class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Integer) fieldValue);
        }
        if (Long.class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Long) fieldValue);
        }
        if (Timestamp.class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Timestamp)fieldValue);
        }
        if (Date.class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Date)fieldValue);
        }
        if (Integer[].class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Integer[]) fieldValue);
        }
        if (Long[].class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Long[]) fieldValue);
        }
        if (Double[].class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Double[]) fieldValue);
        }
        if (Timestamp[].class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Timestamp[]) fieldValue);
        }
        if (Date[].class.isAssignableFrom(fieldType) && fieldValue != null) {
            predicate = generateCondition(root, builder, fieldName, (Date[]) fieldValue);
        }
        if (predicate != null) {
            predicates.add(predicate);
        }
        return predicates;
    }

    //单字段
    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, String value) {
        return builder.like(root.get(key),value + "%");
    }

    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Integer value) {
        return builder.equal(root.get(key), value);
    }

    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Long value) {
        return builder.equal(root.get(key), value);
    }

    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Timestamp value) {
        return builder.equal(root.get(key), value);
    }

    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Date value) {
        return builder.equal(root.get(key), value);
    }

    //范围
    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Integer[] values) {
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

    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Long[] values) {
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

    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Double[] values) {
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

    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Timestamp[] values) {
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

    private static Predicate generateCondition(From<?, ?> root, CriteriaBuilder builder, String key, Date[] values) {
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
