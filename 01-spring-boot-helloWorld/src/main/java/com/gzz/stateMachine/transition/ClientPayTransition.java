package com.gzz.stateMachine.transition;

import com.gzz.stateMachine.OrderTransitionContext;
import com.gzz.stateMachine.entity.OrderStatus;
import com.gzz.stateMachine.event.OrderTransitionEvent;
import com.gzz.stateMachine.interfaces.OrderTransition;
import com.gzz.stateMachine.interfaces.OrderTransitionI;
import lombok.extern.slf4j.Slf4j;

@OrderTransition(key = "CLIENT_PAY", name = "客户端支付同步",
        from = {OrderStatus.NONE}, to = OrderStatus.OS10,
        on = OrderTransitionEvent.CLIENT_PAY
)
@Slf4j
public class ClientPayTransition implements OrderTransitionI {
    @Override
    public boolean checkCondition(OrderTransitionContext context) {
        log.info("客户端支付同步校验");
        return true;
    }

    @Override
    public void doAction(OrderStatus from, OrderStatus to, OrderTransitionEvent event, OrderTransitionContext context) {
        log.info("客户端支付同步");
    }

}
