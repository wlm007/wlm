package com.wlm.wlm.params.sysMenu;

import com.wlm.wlm.model.sys.SysMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wuliming
 * @date 2022/1/19 16:21
 */
@Data
public class SysMenuSaveParams {

    @ApiModelProperty("菜单主键(有更新 无新增)")
    private Integer id;

    @NotNull(message = "父级菜单不能为空")
    @ApiModelProperty("父级菜单主键，0=顶级菜单无父级")
    private Integer parentId;

    @NotNull(message = "菜单类型不能为空")
    @ApiModelProperty("菜单类型 0=菜单组 1=功能菜单")
    private Integer menuType;

    @NotBlank(message = "菜单名不能为空")
    @ApiModelProperty("菜单名")
    private String menuName;

    @NotNull(message = "菜单排序位置不能为空")
    @ApiModelProperty("菜单在本菜单组中的排序位置")
    private Integer menuSort;

    @NotBlank(message = "功能菜单对应的模块名不能为空")
    @ApiModelProperty("功能菜单对应的模块名")
    private String menuFun;

    public SysMenu getMenu() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setMenuFun(menuFun);
        sysMenu.setMenuSort(menuSort);
        sysMenu.setMenuName(menuName);
        sysMenu.setMenuType(menuType);
        sysMenu.setParentId(parentId);
        return sysMenu;
    }
}
