package com.wlm.wlm.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公共分页参数
 * @author wuliming
 * @date 2021/7/29 10:23
 */
@Data
public class PageParam {

    @ApiModelProperty("分页-第几页")
    private Integer pageNo;

    @ApiModelProperty("分页-每页数")
    private Integer pageSize;
}
