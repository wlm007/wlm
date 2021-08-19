package com.wlm.wlm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * sys_dept
 * @author wuliming
 */
@Data
public class SysDept implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 部门编号
     */
    private String deptNo;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 是否删除0=否,1=是
     */
    private Integer idDelete;

    private static final long serialVersionUID = 1L;
}