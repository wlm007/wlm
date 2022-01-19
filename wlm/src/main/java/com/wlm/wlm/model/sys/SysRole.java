package com.wlm.wlm.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * sys_role
 * @author wuliming
 */
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 8901067434172081978L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
}