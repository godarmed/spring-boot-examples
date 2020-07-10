package com.zzy.kafka.messageHandler.service;

import java.util.Map;

/**
 * @Classname MessageHandler
 * @Description TODO
 * @Date 2020/6/17 15:21
 * @Created by Zzy
 */
public interface MessageHandlerInterface {

    /**
     * 初始化消息
     * @param msg
     * @return
     */
    Map<String,Object> initMsg(String msg);

    /**
     * 校验消息
     * @param msgMap
     * @return
     */
    Map<String,Object> checkMsg(Map<String,Object> msgMap);

    /**
     * 发送前消息处理
     * @param msgMap
     * @return
     */
    Map<String,Object> preSendMsg(Map<String,Object> msgMap);


    /**
     * 发送消息
     * @param msgMap
     * @return
     */
    Map<String,Object> sendMsg(Map<String,Object> msgMap);


    /**
     * 发送后消息处理
     * @param msgMap
     * @return
     */
    Map<String,Object> afterSendMsg(Map<String,Object> msgMap);

}
