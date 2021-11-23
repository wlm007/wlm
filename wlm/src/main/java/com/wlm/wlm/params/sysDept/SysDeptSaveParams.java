package com.wlm.wlm.params.sysDept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 系统部门保存参数
 * @author wuliming
 * @date 2021/11/23 10:26
 */
@Data
@NoArgsConstructor
public class SysDeptSaveParams {

    @ApiModelProperty("部门id 有则更新 无则新增")
    private Integer id;

    @NotBlank(message = "部门名称不能为空")
    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("父级部门编号")
    private String parentNo;
}
