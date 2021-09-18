package com.wlm.wlm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_role
 * @author wuliming
 */
@Data
public class SysRole implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色编号
     */
    private String roleNo;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否删除0=否,1=是
     */
    private Integer idDelete;

    private static final long serialVersionUID = 1L;
}