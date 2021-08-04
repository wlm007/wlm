package com.wlm.wlm.params.sysUser;

import com.wlm.wlm.config.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuliming
 * @date 2021/7/29 10:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserListParams extends PageParam {

    @ApiModelProperty("角色编号")
    private String roleNo;

    @ApiModelProperty("部门编号")
    private String deptNo;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("角色名称")
    private String roleName;
}
