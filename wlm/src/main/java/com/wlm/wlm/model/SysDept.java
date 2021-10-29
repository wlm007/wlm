package com.wlm.wlm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * sys_dept
 * @author wuliming
 */
@Data
public class SysDept implements Serializable {

    private static final long serialVersionUID = 6094723419834979242L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 部门编号
     */
    private String deptNo;

    /**
     * 父级部门，顶级无父级部门为root
     */
    private String parentNo;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 是否删除0=否,1=是
     */
    private Integer isDelete;
}