package com.wlm.wlm.params.sysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * 用户信息更新
 * @author wuliming
 * @date 2021/10/26 17:54
 */
@Data
public class SysUserUpdateParams {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("角色编号")
    private String roleNo;

    @ApiModelProperty("部门编号")
    private String deptNo;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("邮箱")
    private String email;
}
