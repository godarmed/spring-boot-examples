package com.gzz.common.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gzz.common.model.JoinField;
import com.gzz.common.model.SortField;
import com.gzz.common.model.dto.BaseQueryDTO;
import com.gzz.common.model.po.BasePO;
import com.gzz.common.utils.reflex.ReflexEntity;
import com.gzz.common.utils.reflex.ReflexUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Classname Resource
 * @Description TODO
 * @Date 2020/6/21 11:10
 * @Created by Zzy
 */

public class ResourcesPredicate<PO extends BasePO, Q extends BaseQueryDTO> implements Specification<PO> {

    private static final long serialVersionUID = 1L;

    private static Map joinTypeMap = new HashMap<Integer, String>();

    static{
        joinTypeMap.put(0,"一对一");
        joinTypeMap.put(1,"一对多");
        joinTypeMap.put(2,"多对多");
    }

    private List<Predicate> predicates = Lists.newArrayList();
    private List<String> excludes = new ArrayList<String>() {
        {
            add("page");
            add("size");
            add("serialVersionUID");
        }
    };
    private Q request;

    public ResourcesPredicate(Q request) {
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<PO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // TODO Auto-generated method stub
        try {
            //拼装查询参数
            resourceSearch(root, query, builder);
            //拼装排序数据
            List<Order> orders = generateSort(root, query, builder);
            query.orderBy(orders);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private <J extends BasePO> List<Order> generateSort(Root<PO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Order> orders = new ArrayList<>();
        Map<String, JoinField> JoinFieldMap = request.getJoinFieldList().stream().
                collect(Collectors.toMap(JoinField::getFieldName, Function.identity()));
        if (request.getSortFieldList() != null) {
            request.getSortFieldList().forEach(item -> {
                Order order = null;
                if (JoinFieldMap.containsKey(item.getFieldName())) {
                    JoinField joinField = JoinFieldMap.get(item.getFieldName());
                    switch(joinField.getJoinType()){
                        case 0: //一对一
                            Join<PO, J> join = root.join(joinField.getTableName(), JoinType.LEFT);
                            if (item.getDirection() == 1) {      //升序
                                order = builder.asc(join.get(item.getFieldName()));
                            } else {                         //降序
                                order = builder.desc(join.get(item.getFieldName()));
                            }
                            break;
                        case 1: //一对多
                            break;
                        case 2: //多对多
                            break;
                        default:
                    }
                } else {
                    if (item.getDirection() == 1) {      //升序
                        order = builder.asc(root.get(item.getFieldName()));
                    } else {                         //降序
                        order = builder.desc(root.get(item.getFieldName()));
                    }
                }
                orders.add(order);
            });
        }
        if (orders.size() == 0) {
            orders.add(builder.desc(root.get("id")));
        }
        return orders;
    }

    private void resourceSearch(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder) throws IllegalArgumentException, IllegalAccessException {
        List<ReflexEntity> enties = ReflexUtils.reflex2EntityAndSuperClass(request);
        if (enties != null && enties.size() > 0) {
            request.getJoinFieldList().forEach(item -> {
                excludes.add(item.getFieldName());
            });
            Class<?> type = null;
            String fieldName = null;
            Object value = null;
            for (ReflexEntity entity : enties) {
                fieldName = entity.getName();
                if (excludes.contains(entity.getName())) {
                    continue;
                }
                type = entity.getType();
                value = entity.getValue();
                if (String.class.isAssignableFrom(type) && value != null && !"".equals(value)) {
                    predicates.add(string(root, query, builder, fieldName, String.valueOf(value)));
                }
                if (Integer.class.isAssignableFrom(type) && value != null) {
                    predicates.add(integer(root, query, builder, fieldName, (Integer) value));
                }
                if (Long.class.isAssignableFrom(type) && value != null) {
                    predicates.add(longType(root, query, builder, fieldName, (Long) value));
                }
                if (Timestamp[].class.isAssignableFrom(type) && value != null) {
                    Predicate predicate = timestampType(root, query, builder, fieldName, (Timestamp[]) value);
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                } else if (Timestamp.class.isAssignableFrom(type) && value != null) {
                    Predicate predicate = timestampType(root, query, builder, fieldName, new Timestamp[]{(Timestamp) value, null});
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
            }
            if (request.getJoinFieldList() != null && request.getJoinFieldList().size() > 0) {
                for (JoinField joinField : request.getJoinFieldList()) {
                    resourceSearchParent(root, query, builder, joinField);
                }
            }
        }
    }

    private <J extends BasePO> void resourceSearchParent(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, JoinField joinField) {
        //映射表的实际字段
        String fieldName = joinField.getFieldName();
        if(joinField.getAliasName() != null){
            fieldName = joinField.getAliasName();
        }
        Class<?> type = null;
        Object value = null;
        //多种连表情况的处理
        switch(joinField.getJoinType()){
            type = joinField.getFieldType();
            value = joinField.getFieldValue();
            case 0: //一对一
                Join<PO, J> join = root.join(joinField.getTableName(), JoinType.LEFT);
                if (String.class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null && !"".equals(joinField.getFieldValue())) {
                    predicates.add(string(join, query, builder, fieldName, String.valueOf(joinField.getFieldValue())));
                }
                if (Integer.class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null) {
                    predicates.add(integer(join, query, builder, fieldName, (Integer) joinField.getFieldValue()));
                }
                if (Long.class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null) {
                    predicates.add(longType(join, query, builder, fieldName, (Long) joinField.getFieldValue()));
                }
                if (Timestamp[].class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null) {
                    Predicate predicate = timestampType(join, query, builder, fieldName, (Timestamp[]) joinField.getFieldValue());
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                } else if (Timestamp.class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null) {
                    Predicate predicate = timestampType(join, query, builder, fieldName, new Timestamp[]{(Timestamp) joinField.getFieldValue(), null});
                    if (predicate != null) {
                        predicates.add(predicate);
                    }
                }
                break;
            case 1: //一对多
                break;
            case 2: //多对多
                break;
            default:
        }
    }

    private Predicate generateCondition(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, JoinField joinField){
        if (String.class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null && !"".equals(joinField.getFieldValue())) {
            predicates.add(string(root, query, builder, fieldName, String.valueOf(joinField.getFieldValue())));
        }
        if (Integer.class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null) {
            predicates.add(integer(root, query, builder, fieldName, (Integer) joinField.getFieldValue()));
        }
        if (Long.class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null) {
            predicates.add(longType(root, query, builder, fieldName, (Long) joinField.getFieldValue()));
        }
        if (Timestamp[].class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null) {
            Predicate predicate = timestampType(root, query, builder, fieldName, (Timestamp[]) joinField.getFieldValue());
            if (predicate != null) {
                predicates.add(predicate);
            }
        } else if (Timestamp.class.isAssignableFrom(joinField.getFieldType()) && joinField.getFieldValue() != null) {
            Predicate predicate = timestampType(root, query, builder, fieldName, new Timestamp[]{(Timestamp) joinField.getFieldValue(), null});
            if (predicate != null) {
                predicates.add(predicate);
            }
        }
    }



    private Predicate string(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, String key, String value) {
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

