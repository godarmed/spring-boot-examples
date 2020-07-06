package com.gzz.common.utils.jpaSearch;

import com.google.common.collect.Lists;
import com.gzz.common.model.JoinField;
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
            add("sortFieldList");
            add("joinFieldList");
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
            resourceSearch(root, builder);
            //拼装排序数据
            List<Order> orders = generateSort(root, builder);
            query.orderBy(orders);
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 排序规则组装
     * @param root
     * @param builder
     * @param <J>
     * @return
     */
    private <J extends BasePO> List<Order> generateSort(Root<PO> root, CriteriaBuilder builder) {
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

    /**
     * 一般条件组装
     * @param root
     * @param builder
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void resourceSearch(From<?, ?> root, CriteriaBuilder builder) throws IllegalArgumentException, IllegalAccessException {
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
                ConditionDTO conditionDTO = new ConditionDTO(root, builder, fieldName, null ,type ,value);
                FieldConditionUtil.addCondition(conditionDTO, predicates);
            }
            if (request.getJoinFieldList() != null && request.getJoinFieldList().size() > 0) {
                for (JoinField joinField : request.getJoinFieldList()) {
                    resourceSearchParent(root, builder, joinField);
                }
            }
        }
    }

    /**
     * 连表查询条件组装
     * @param root
     * @param builder
     * @param joinField
     * @param <J>
     */
    private <J extends BasePO> void resourceSearchParent(From<?, ?> root, CriteriaBuilder builder, JoinField joinField) {
        //映射表的实际字段
        String fieldName = joinField.getFieldName();
        String aliasName = joinField.getAliasName();
        Class<?> type = joinField.getFieldType();
        Object value = joinField.getFieldValue();
        String tableName = joinField.getTableName();
        //多种连表情况的处理
        switch(joinField.getJoinType()){
            case 0: //一对一
                Join<PO, J> join = root.join(tableName, JoinType.LEFT);
                ConditionDTO conditionDTO = new ConditionDTO(join, builder, fieldName, aliasName ,type ,value);
                FieldConditionUtil.addCondition(conditionDTO, predicates);
                break;
            case 1: //一对多
                break;
            case 2: //多对多
                break;
            default:
        }
    }

    /**
     * 特殊条件组装
     * @param root
     * @param query
     * @param builder
     * @param predicates
     */
    private void extraSearch(From<?, ?> root, CriteriaQuery<?> query, CriteriaBuilder builder, List<Predicate> predicates){

    }
}

