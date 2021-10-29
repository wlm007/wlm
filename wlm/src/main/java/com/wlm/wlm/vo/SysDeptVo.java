package com.wlm.wlm.vo;

import com.wlm.wlm.model.SysDept;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统部门vo
 * @author wuliming
 * @date 2021/10/29 11:52
 */
@Data
public class SysDeptVo {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("部门编号")
    private String deptNo;

    @ApiModelProperty("父级部门，顶级无父级部门为root")
    private String parentNo;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("是否删除0=否,1=是")
    private Integer isDelete;

    public SysDeptVo(SysDept sysDept) {
        this.id = sysDept.getId();
        this.deptNo = sysDept.getDeptNo();
        this.parentNo = sysDept.getParentNo();
        this.deptName = sysDept.getDeptName();
        this.isDelete = sysDept.getIsDelete();
    }
}
