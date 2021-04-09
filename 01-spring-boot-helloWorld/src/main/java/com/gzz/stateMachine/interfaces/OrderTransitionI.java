package com.gzz.stateMachine.interfaces;

import com.gzz.stateMachine.OrderTransitionContext;
import com.gzz.stateMachine.entity.OrderStatus;
import com.gzz.stateMachine.event.OrderTransitionEvent;

public interface OrderTransitionI {
    boolean checkCondition(OrderTransitionContext context);

    void doAction(OrderStatus from, OrderStatus to, OrderTransitionEvent event, OrderTransitionContext context);
}
