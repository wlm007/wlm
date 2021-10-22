package com.wlm.wlm.config;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * api统一返回结果
 * @author wuliming
 * @date 2021/7/20 16:43
 */
@Data
@NoArgsConstructor
public class ApiResult<T> {

    /**
     * 通用code
     */
    public static final Integer OK = 200;
    public static final Integer CUSTOM_ERROR = 500;
    public static final Integer ERROR = 520;

    /**
     * 安全认证登录异常
     */
    public static final Integer NOT_LOGIN_OR_OUT = 1001;
    public static final Integer LOGIN_ERROR = 1002;
    public static final Integer NO_AUTHORITY = 1003;
    public static final Integer NOT_EXIST = 1004;


    private Integer code = 200;

    private String msg = "success";

    private T data;

    public ApiResult(T data) {
        this.data = data;
    }

    public ApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
