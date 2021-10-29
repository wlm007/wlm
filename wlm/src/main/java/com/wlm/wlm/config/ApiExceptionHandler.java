package com.wlm.wlm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;


/**
 * 接口统一异常拦截封装
 * @author wuliming
 * @date 2021/7/29 17:46
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ApiResult<String> businessException(ApiException e) {
        logger.info(e.getMessage());
        return new ApiResult<>(e.getCode(), e.getMessage());
    }

    /**
     * 请求参数验证错误异常
     * @param e 捕获的异常
     * @return ApiResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResult<String> paramsException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("；"));
        logger.error(errorMsg);
        return new ApiResult<>(ApiResult.ERROR, errorMsg);
    }

    /**
     * 缺少路径参数（PathVariable）错误处理
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApiResult<String> handleLackPathParam(MethodArgumentTypeMismatchException ex) {
        String errMsg = "缺少路径参数" + ex.getName();
        logger.error(errMsg);
        return new ApiResult<>(ApiResult.ERROR, errMsg);
    }

    /**
     * 缺少请求参数（RequestVariable/QueryString）错误处理
     *
     * @param ex 捕获的错误
     * @return 带错误提示的返回结果
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult<String> handleLackQueryParam(MissingServletRequestParameterException ex) {
        String errMsg = "缺少请求参数" + ex.getParameterName();
        logger.error(errMsg);
        return new ApiResult<>(ApiResult.ERROR, errMsg);
    }

    /**
     * 通用异常处理
     * @param e 捕获的异常
     * @return ApiResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult<String> runtimeException(Exception e) {
        e.printStackTrace();
        logger.info(e.getMessage());
        return new ApiResult<>(ApiResult.ERROR, "后端请求错误，请稍后重试");
    }
}
