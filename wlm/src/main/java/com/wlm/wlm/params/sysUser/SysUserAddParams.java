package com.wlm.wlm.params.sysUser;

import com.wlm.wlm.model.sys.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wuliming
 * @date 2021/7/20 16:31
 */
@Data
public class SysUserAddParams implements Serializable {

    private static final long serialVersionUID = 967669563127635281L;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("部门编号")
    private String deptNo;

    @ApiModelProperty("角色编号")
    private String roleNo;

    public SysUser getSysUser() {
        SysUser user = new SysUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setAge(age);
        user.setEmail(email);
        user.setDeptNo(deptNo);
        user.setRoleNo(roleNo);
        return user;
    }
}
