package com.wlm.wlm.vo;

import com.wlm.wlm.model.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuliming
 * @date 2021/7/28 17:28
 */
@Data
@NoArgsConstructor
public class SysUserVo {

    @ApiModelProperty("自增id")
    private Integer id;

    @ApiModelProperty("姓名")
    private String username;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("部门编号")
    private String deptNo;

    @ApiModelProperty("角色编号")
    private String roleNo;

    @ApiModelProperty("登录token")
    private String token;

    @ApiModelProperty("刷新token")
    private String refreshToken;

    public SysUserVo(SysUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.age = user.getAge();
        this.email = user.getEmail();
        this.deptNo = user.getDeptNo();
        this.roleNo = user.getRoleNo();
    }
}
