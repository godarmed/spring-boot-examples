package com.zzy.kafka.messageHandler.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Classname MessageHandlerProxy
 * @Description TODO
 * @Date 2020/6/17 15:33
 * @Created by Zzy
 */
@Service
public class MessageHandlerProxy {

    public Map<String,Object> msgProcess(String msg){
        //初始化消息
        Map<String,Object> msgMap;
        //校验消息
        //发送消息
        return null;
    }


    /**
     * 校验消息
     * @param msgMap
     * @return
     */
    private Map<String,Object> checkMsg(Map<String,Object> msgMap){
        return msgMap;
    }

    /**
     * 发送前消息处理
     * @param msgMap
     * @return
     */
    private Map<String,Object> preSendMsg(Map<String,Object> msgMap){
        return msgMap;
    }


    /**
     * 发送消息
     * @param msgMap
     * @return
     */
    Map<String,Object> sendMsg(Map<String,Object> msgMap){
       return msgMap;
    }



    /**
     * 发送后消息处理
     * @param msgMap
     * @return
     */
    private Map<String,Object> afterSendMsg(Map<String,Object> msgMap){
        return msgMap;
    }



}
