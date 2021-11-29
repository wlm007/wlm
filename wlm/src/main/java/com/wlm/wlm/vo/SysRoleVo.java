package com.wlm.wlm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuliming
 * @date 2021/11/24 15:19
 */
@Data
@NoArgsConstructor
public class SysRoleVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("角色编号")
    private String roleNo;

    @ApiModelProperty("角色名称")
    private String roleName;
}
