package com.wlm.wlm.config;

import io.swagger.annotations.ApiModelProperty;

/**
 * 公共分页参数
 * @author wuliming
 * @date 2021/7/29 10:23
 */
public class PageParam {

    @ApiModelProperty("分页-第几页, 默认1")
    private Integer pageNo;

    @ApiModelProperty("分页-每页数, 默认30")
    private Integer pageSize;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo == null || pageNo == 0) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize == 0) {
            this.pageSize = 30;
        } else {
            this.pageSize = pageSize;
        }
    }
}
