package com.gzz.stateMachine.interfaces;

import com.gzz.stateMachine.entity.OrderStatus;
import com.gzz.stateMachine.event.OrderTransitionEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface OrderTransition {
    String key();

    String name();

    OrderStatus[] from() default {};

    OrderStatus in() default OrderStatus.NONE;

    OrderStatus to() default OrderStatus.NONE;

    OrderTransitionEvent on();
}
