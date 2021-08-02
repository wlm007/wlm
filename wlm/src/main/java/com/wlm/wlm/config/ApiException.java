package com.wlm.wlm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 项目统一异常类
 * @author wuliming
 * @date 2021/7/29 17:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends RuntimeException{

    private Integer code = 500;

    private String message;

    public ApiException(String message) {
        this.message = message;
    }
}
