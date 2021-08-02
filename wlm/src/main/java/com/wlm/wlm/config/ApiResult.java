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
