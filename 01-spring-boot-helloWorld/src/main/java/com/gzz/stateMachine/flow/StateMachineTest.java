package com.gzz.stateMachine.flow;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.gzz.stateMachine.OrderTransitionBootstrap;
import com.gzz.stateMachine.OrderTransitionContext;
import com.gzz.stateMachine.entity.OrderStatus;
import com.gzz.stateMachine.event.OrderTransitionEvent;
import org.springframework.stereotype.Service;

@Service
public class StateMachineTest {

    public static StateMachine<OrderStatus, OrderTransitionEvent, OrderTransitionContext> getOrderMachine() {
        return StateMachineFactory.get(OrderTransitionBootstrap.MACHINE_FOR_ORDER_ID);
    }

    public OrderStatus sendOrderEvent(OrderTransitionEvent event, OrderTransitionContext ctx) {
        return getOrderMachine().fireEvent(ctx.getSourceStatus(), event, ctx);
    }

}
