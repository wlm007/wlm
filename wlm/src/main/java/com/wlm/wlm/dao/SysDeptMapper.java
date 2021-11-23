package com.wlm.wlm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wlm.wlm.model.SysDept;
import com.wlm.wlm.vo.SysDeptVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuliming
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 获取全部部门
     * @param deptName 部门名称
     * @return 部门列表
     */
    List<SysDeptVo> listDept(@Param("deptName") String deptName);

    /**
     * 根据父级编号获取最大子级部门编号
     * @param parentNo 父级部门编号
     * @return 最大子级部门编号
     */
    String getDeptNoByParentNo(@Param("parentNo") String parentNo);

    /**
     * 根据id更新部门名称
     * @param id id
     * @param deptName 新部门名称
     */
    void updateDeptNameById(@Param("id") Integer id, @Param("deptName") String deptName);
}