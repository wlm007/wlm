package com.wlm.wlm.params.sysDept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统部门list请求参数
 * @author wuliming
 * @date 2021/10/29 11:51
 */
@Data
public class SysDeptListParams {

    @ApiModelProperty("部门名称")
    private String deptName;
}
