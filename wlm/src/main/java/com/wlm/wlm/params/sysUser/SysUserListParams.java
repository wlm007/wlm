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
}
