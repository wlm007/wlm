package com.wlm.wlm.params.sysRole;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 保存角色params
 * @author wuliming
 * @date 2021/11/24 16:48
 */
@Data
@NoArgsConstructor
public class SysRoleSaveParams {

    @ApiModelProperty("id 有更新 无新增")
    private Integer id;

    @NotBlank(message = "角色编号不能为空")
    @ApiModelProperty("角色编号")
    private String roleNo;

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty("角色名称")
    private String roleName;
}
