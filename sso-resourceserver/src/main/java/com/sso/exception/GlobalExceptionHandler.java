package com.sso.exception;

import com.sso.entity.ResponseResult;
import com.sso.entity.enums.GlobalCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * method of service exception handing
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseResult<?> processServiceException(HttpServletResponse response, ServiceException e) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(e.getCode());
        responseResult.setMessage(e.getMessage());
        log.error(e.toString() + "_" + e.getMessage(), e);
        return responseResult;
    }

    /**
     * parameter verification error handing method 1
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult<?> processValidException(HttpServletResponse response, MethodArgumentNotValidException e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        List<String> errorStringList = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        String errorMessage = String.join("; ", errorStringList);
        response.setContentType("application/json;charset=UTF-8");
        log.error(e.toString() + "_" + e.getMessage(), e);
        return ResponseResult.systemException(GlobalCodeEnum.GL_FAIL_9998);
    }

    /**
     * parameter verification error handing method 2
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseResult<?> processValidException(HttpServletResponse response, BindException e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        List<String> errorStringList = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        String errorMessage = String.join("; ", errorStringList);
        response.setContentType("application/json;charset=UTF-8");
        log.error(e.toString() + "_" + e.getMessage(), e);
        return ResponseResult.systemException(GlobalCodeEnum.GL_FAIL_9998);
    }

    /**
     * parameter verification error handing method 1
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseResult<?> processValidException(HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String[] supportedMethods = e.getSupportedMethods();
        String errorMessage = "this interface is not support" + e.getMethod();
        if (!ArrayUtils.isEmpty(supportedMethods)) {
            errorMessage += " (only support" + String.join(",", supportedMethods) + ")";
        }
        response.setContentType("application/json;charset=UTF-8");
        log.error(e.toString() + "_" + e.getMessage(), e);
        return ResponseResult.systemException(GlobalCodeEnum.GL_FAIL_9996);
    }

    /**
     * method of unknow system exception handing
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseResult<?> processDefaultException(HttpServletResponse response, ServiceException e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        log.error(e.toString() + "_" + e.getMessage(), e);
        return ResponseResult.systemException();
    }
}