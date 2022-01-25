package com.wlm.wlm.model.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * sys_menu 系统菜单
 * @author wuliming
 * @date 2021-10-19
 */
@Data
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 6267776258027253809L;
    /**
     * 菜单主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 父级菜单主键，0=顶级菜单无父级
     */
    private Integer parentId;

    /**
     * 菜单类型 0=菜单组 1=功能菜单
     */
    private Integer menuType;

    /**
     * 菜单名
     */
    private String menuName;

    /**
     * 菜单在本菜单组中的排序位置
     */
    private Integer menuSort;

    /**
     * 功能菜单对应的模块名
     */
    private String menuFun;

}