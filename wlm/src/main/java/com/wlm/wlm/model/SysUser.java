package com.wlm.wlm.model;

import lombok.Data;

/**
 * @author wuliming
 * @date 2021/7/20 14:52
 */
@Data
public class SysUser {

    private Long id;

    private Integer age;

    private String username;

    private String password;

    private String email;

    private String deptNo;

    private String roleNo;
}
