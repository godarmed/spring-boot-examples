package com.gzz.stateMachine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gzz.stateMachine.entity.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderTransitionContext implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean isMatch = false;

    private OrderStatus sourceStatus = OrderStatus.NONE;

    private String orderSn;

    private JSONArray context = new JSONArray();

    public OrderTransitionContext(String orderSn, Object data) {
        setOrderSn(orderSn);
        if (data != null) {
            context.add(JSON.parseObject(JSON.toJSONString(data)));
        }
    }

    public <T> List<T> getContext(Class<T> targetClazz) {
        if (context != null) {
            return JSON.parseArray(context.toJSONString(), targetClazz);
        }
        return null;
    }

    public <T> T getFirstContextValue(Class<T> targetClazz) {
        if (context != null) {
            List<T> result = getContext(targetClazz);
            if (result != null && result.size() > 0) {
                return result.get(0);
            }
        }
        return null;
    }
}
