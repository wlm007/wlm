package com.wlm.wlm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统部门vo
 * @author wuliming
 * @date 2021/10/29 11:52
 */
@Data
@NoArgsConstructor
public class SysDeptVo {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("部门编号")
    private String deptNo;

    @ApiModelProperty("父级部门，顶级无父级部门为空")
    private String parentNo;

    @ApiModelProperty("父级部门名称")
    private String parentDeptName;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("是否删除0=否,1=是")
    private Integer isDelete;

    @ApiModelProperty("下一级部门列表")
    private List<SysDeptVo> children = new ArrayList<>();
}
