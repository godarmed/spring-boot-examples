package com.zzy.activiti.common.exception;

/**
 * @Classname exception
 * @Description TODO
 * @Date 2020/10/21 14:51
 * @Created by Zzy
 */
public class ActivitiEseaskyException extends BaseHandlerException {

    private static final long serialVersionUID = 1L;

    public ActivitiEseaskyException(int code, String message) {
        super(code, message);
    }

    public ActivitiEseaskyException(String message) {
        super(500, message);
    }

}

