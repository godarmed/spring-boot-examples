package com.gzz.stateMachine;

import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.gzz.stateMachine.entity.OrderStatus;
import com.gzz.stateMachine.event.OrderTransitionEvent;
import com.gzz.stateMachine.interfaces.OrderTransition;
import com.gzz.stateMachine.interfaces.OrderTransitionI;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Log4j2
public class OrderTransitionBootstrap implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    public static final String MACHINE_FOR_ORDER_ID = "OrderTransitionBootstrap";

    public static final String MACHINE_FOR_ORDER_REFUND_ID = "OrderTransitionBootstrap_REFUND";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        orderTransitionInit();
    }

    /**
     * 订单状态机注册
     */
    private void orderTransitionInit() {
        StateMachineBuilder<OrderStatus, OrderTransitionEvent, OrderTransitionContext> machineBuilder = StateMachineBuilderFactory.create();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(OrderTransition.class);
        beans.values().forEach(transition -> {
            OrderTransition anno = transition.getClass().getAnnotation(OrderTransition.class);
            OrderTransitionI transitionBean = ((OrderTransitionI) transition);
            buildForOrder(anno, transitionBean, machineBuilder);
        });
        machineBuilder.build(MACHINE_FOR_ORDER_ID);
    }

    private void buildForOrder(OrderTransition anno, OrderTransitionI transitionBean,
                               StateMachineBuilder<OrderStatus, OrderTransitionEvent, OrderTransitionContext> machineBuilder) {
        if (!anno.in().equals(OrderStatus.NONE)) {
            machineBuilder.internalTransition()
                    .within(anno.in())
                    .on(anno.on())
                    .when(ctx -> {
                        return doCheck(transitionBean, ctx, anno.on());
                    })
                    .perform((from, to, event, ctx) -> transitionBean.doAction(from, to, event, ctx));
        } else if (anno.from().length > 1) {
            machineBuilder.externalTransitions()
                    .fromAmong(anno.from())
                    .to(anno.to())
                    .on(anno.on())
                    .when(ctx -> {
                        return doCheck(transitionBean, ctx, anno.on());
                    })
                    .perform((from, to, event, ctx) -> transitionBean.doAction(from, to, event, ctx));
        } else {
            machineBuilder.externalTransition()
                    .from(anno.from()[0])
                    .to(anno.to())
                    .on(anno.on())
                    .when(ctx -> {
                        return doCheck(transitionBean, ctx, anno.on());
                    })
                    .perform((from, to, event, ctx) -> transitionBean.doAction(from, to, event, ctx));
        }
    }

    private boolean doCheck(OrderTransitionI transitionBean, OrderTransitionContext ctx, OrderTransitionEvent event) {
        boolean result = transitionBean.checkCondition(ctx);
        log.debug("[{}] match event [{}], check result: {}", ctx.getOrderSn(), event.toString(), result);
        ctx.setMatch(result);
        return result;
    }


}
