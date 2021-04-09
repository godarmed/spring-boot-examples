package com.gzz.stateMachine.controller;

import com.alibaba.fastjson.JSONObject;
import com.gzz.stateMachine.OrderTransitionContext;
import com.gzz.stateMachine.entity.OrderStatus;
import com.gzz.stateMachine.event.OrderTransitionEvent;
import com.gzz.stateMachine.flow.StateMachineTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@RestController
public class StateController {

    @Resource
    StateMachineTest stateMachineTest;

    @PostMapping("/stateMachineTest")
    public OrderStatus stateMachineTest(@RequestBody JSONObject data) {
        log.info("stateMachineTest");
        OrderTransitionContext ctx = new OrderTransitionContext("orderSn", null);
        return stateMachineTest.sendOrderEvent(OrderTransitionEvent.CLIENT_PAY, ctx);
    }

}
