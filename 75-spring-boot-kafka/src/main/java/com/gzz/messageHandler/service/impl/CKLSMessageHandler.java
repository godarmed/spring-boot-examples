package com.gzz.messageHandler.service.impl;

import com.gzz.messageHandler.model.Constants;
import com.gzz.messageHandler.service.AbstractMessageHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Classname MessageAHandler
 * @Description TODO
 * @Date 2020/6/17 15:25
 * @Created by Zzy
 */
@Service("MessageHandler" + Constants.msgType.CKLS)
public class CKLSMessageHandler extends AbstractMessageHandler {

    @Override
    public Map<String, Object> checkMsg(Map<String, Object> msgMap) {
        return msgMap;
    }

    @Override
    public Map<String, Object> preSendMsg(Map<String, Object> msgMap) {
        return msgMap;
    }

    @Override
    public Map<String, Object> afterSendMsg(Map<String, Object> msgMap) {
        return msgMap;
    }

    @Override
    public Map<String, Object> sendMsg(Map<String, Object> msgMap) {
        return null;
    }
}
