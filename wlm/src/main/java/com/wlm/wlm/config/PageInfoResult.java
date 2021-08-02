package com.wlm.wlm.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一分页数据返回封装
 * @author wuliming
 * @date 2021/7/29 10:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoResult<T> {

    @ApiModelProperty("总数")
    private Long total;

    @ApiModelProperty("数据")
    private T data;
}
