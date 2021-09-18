package com.wlm.wlm.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 接口统一异常拦截封装
 * @author wuliming
 * @date 2021/7/29 17:46
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ApiResult<String> businessException(ApiException e) {
        e.printStackTrace();
        return new ApiResult<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult<String> runtimeException(Exception e) {
        e.printStackTrace();
        return new ApiResult<>(500, "后端请求错误，请稍后重试");
    }
}
