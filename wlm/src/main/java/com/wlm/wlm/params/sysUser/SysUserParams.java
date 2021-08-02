package com.wlm.wlm.params.sysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuliming
 * @date 2021/7/20 16:31
 */
@Data
public class SysUserParams {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
