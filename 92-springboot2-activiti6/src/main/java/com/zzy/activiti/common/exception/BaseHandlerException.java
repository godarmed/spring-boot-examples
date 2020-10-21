package com.zzy.activiti.common.exception;

/**
 * @Classname basdhandler
 * @Description TODO
 * @Date 2020/10/21 14:52
 * @Created by Zzy
 */
public class BaseHandlerException extends RuntimeException implements IException {

    private static final long serialVersionUID = 1L;
    private int code = 0;
    private String message = null;

    public BaseHandlerException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"code\":").append(this.code).append(" ,message: \"").append(this.message).append("\"}");
        return sb.toString();
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


}
