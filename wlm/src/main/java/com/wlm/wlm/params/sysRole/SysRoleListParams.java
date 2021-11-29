package com.wlm.wlm.params.sysRole;

import com.wlm.wlm.config.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色列表参数
 * @author wuliming
 * @date 2021/11/24 15:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SysRoleListParams extends PageParam {

    @ApiModelProperty("角色名称")
    private String roleName;
}
