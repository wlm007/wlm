package com.wlm.wlm.vo;

import com.wlm.wlm.model.sys.SysMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统菜单 vo
 * @author wuliming
 * @date 2021-10-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuVo implements Serializable {

    private static final long serialVersionUID = 2462611047235507582L;

    @ApiModelProperty("菜单主键")
    private Integer id;

    @ApiModelProperty("父级菜单主键，0=顶级菜单无父级")
    private Integer parentId;

    @ApiModelProperty("菜单类型 0=菜单组 1=功能菜单")
    private Integer menuType;

    @ApiModelProperty("菜单名")
    private String menuName;

    @ApiModelProperty("菜单在本菜单组中的排序位置")
    private Integer menuSort;

    @ApiModelProperty("功能菜单对应的模块名")
    private String menuFun;

    public SysMenuVo(SysMenu sysMenu) {
        this.id = sysMenu.getId();
        this.parentId = sysMenu.getParentId();
        this.menuType = sysMenu.getMenuType();
        this.menuName = sysMenu.getMenuName();
        this.menuSort = sysMenu.getMenuSort();
        this.menuFun = sysMenu.getMenuFun();
    }
}