package com.zzy.kafka.messageHandler.service;

import com.gzz.messageHandler.util.SpringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname MessageHandlerFactory
 * @Description TODO
 * @Date 2020/6/17 14:22
 * @Created by Zzy
 */
public class MessageHandlerFactory {

    @SuppressWarnings("rawtypes")
    public static final Map<String, MessageHandlerInterface> resourceHandleres = new ConcurrentHashMap<String, MessageHandlerInterface>();

    @SuppressWarnings("rawtypes")
    public static final MessageHandlerInterface getHandler(int resourceType) {
        String beanName = getBeanName(resourceType);
        if (resourceHandleres.containsKey(beanName)) {
            return resourceHandleres.get(beanName);
        } else {
            MessageHandlerInterface bean = SpringUtils.getBean(beanName, MessageHandlerInterface.class);
            if (bean != null) {
                resourceHandleres.put(beanName, bean);
            }
            return bean;
        }
    }

    public static final String getBeanName(int msgType) {
        StringBuilder sb = new StringBuilder();
        sb.append("MessageHandler").append(msgType);
        return sb.toString();
    }
}
