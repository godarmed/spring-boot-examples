package com.zzy.kafka.messageHandler.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname MessageHandlerProxy
 * @Description TODO
 * @Date 2020/6/17 13:13
 * @Created by Zzy
 */
@Service
public abstract class AbstractMessageHandler implements MessageHandlerInterface{

    @Override
    public Map<String,Object> initMsg(String msg){
        Map<String,Object> msgMap = new HashMap<>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(msg);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (null == doc) {
            return msgMap;
        }
        // 获取根元素
        Element rootElement = doc.getRootElement();
        xml2Map(rootElement,msgMap);
        return msgMap;
    }

    /**
     * xml2Map核心方法，递归调用
     *
     * @param element 节点元素
     * @param outmap 用于存储xml数据的map
     */
    private static void xml2Map(Element element, Map<String, Object> outmap) {
        // 得到根元素下的子元素列表
        List<Element> list = element.elements();
        int size = list.size();
        if (size == 0) {
            // 如果没有子元素,则将其存储进map中
            outmap.put(element.getName(), element.getTextTrim());
        } else {
            // innermap用于存储子元素的属性名和属性值
            Map<String, Object> innermap = new HashMap<>();
            // 遍历子元素
            list.forEach(childElement -> xml2Map(childElement, innermap));
            outmap.put(element.getName(), innermap);
        }
    }
}
