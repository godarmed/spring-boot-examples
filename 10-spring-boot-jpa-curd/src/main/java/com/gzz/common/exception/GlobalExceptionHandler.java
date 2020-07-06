package com.gzz.common.exception;

import com.gzz.common.model.vo.ResultModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }


    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultModel<String> httpMediaTypeNotSupportedException(Exception e) {
        ResultModel<String> error = new ResultModel();
        error.setSubCode(500);
        error.setMessage(e.getMessage());
        log.error(e.getMessage(), e);
        return error;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultModel<String> constraintViolationException(ConstraintViolationException e) {
        ResultModel<String> error = new ResultModel();
        error.setSubCode(500);
        error.setMessage(e.getMessage());
        log.error(e.getMessage(), e);
        return error;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultModel<String> serverException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        List<String> message = new ArrayList(errors.size());
        errors.forEach((error) -> {
            message.add(error.getDefaultMessage());
        });
        log.error(e.getMessage(), e);
        ResultModel<String> resultModel = new ResultModel();
        resultModel.setSubCode(500);
        resultModel.setMessage("系统提示：" + message.toString());
        return resultModel;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultModel<String> notReadableException(HttpMessageNotReadableException e) {
        ResultModel<String> resultModel = new ResultModel();
        resultModel.setSubCode(500);
        resultModel.setMessage("系统提示：请求数据包无法解析或者为空");
        log.error(e.getMessage(), e);
        return resultModel;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultModel<String> otherExceptionHandler(Exception e) {
        ResultModel<String> error = new ResultModel();
        this.pickException(e, error);
        log.error(e.getMessage(), e);
        return error;
    }

    private void pickException(Throwable e, ResultModel<String> error) {
        if (e.getCause() != null) {
            this.pickException(e.getCause(), error);
        } else {
            error.setSubCode(500);
            error.setMessage(e.getMessage());
        }

    }

    @ExceptionHandler({NullPointerException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultModel<String> nullPointerException(Exception e) {
        log.error(e.getMessage(), e);
        ResultModel<String> resultModel = new ResultModel();
        resultModel.setSubCode(500);
        resultModel.setMessage("系统提示：服务异常，请联系管理员");
        return resultModel;
    }
}
