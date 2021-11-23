package com.wlm.wlm.service;

import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.dao.SysDeptMapper;
import com.wlm.wlm.model.SysDept;
import com.wlm.wlm.params.sysDept.SysDeptListParams;
import com.wlm.wlm.params.sysDept.SysDeptSaveParams;
import com.wlm.wlm.vo.SysDeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统部门service
 * @author wuliming
 * @date 2021/10/29 11:53
 */
@Service
public class SysDeptServiceImpl {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public List<SysDeptVo> list(SysDeptListParams params) {
        return sysDeptMapper.listDept(params.getDeptName());
    }

    public List<SysDeptVo> listOfTree() {
        List<SysDeptVo> all = sysDeptMapper.listDept(null);
        List<SysDeptVo> result = new ArrayList<>();
        for (SysDeptVo vo : all) {
            if ("root".equals(vo.getDeptNo())) {
                this.treeBuild(vo, all);
                result.add(vo);
            }
        }
        return result;
    }

    /**
     * 部门成树递归方法
     * @param vo 要找到子级的部门
     * @param list 所有的部门
     */
    private void treeBuild(SysDeptVo vo, List<SysDeptVo> list) {
        for (SysDeptVo sysDeptVo : list) {
            if (vo.getDeptNo().equals(sysDeptVo.getParentNo())) {
                vo.getChildren().add(sysDeptVo);
                this.treeBuild(sysDeptVo, list);
            }
        }
    }

    public void save(SysDeptSaveParams params) {
        // 判断是否是更新
        Integer id =  params.getId();
        SysDept sysDept;
        // 更新
        if (id != null && id > 0) {
            sysDept = sysDeptMapper.selectById(id);
            if (sysDept == null) {
                throw new ApiException(ApiResult.ERROR, "该部门不存在");
            }
            sysDeptMapper.updateDeptNameById(id, params.getDeptName());
        } else {
            String parentNo = params.getParentNo();
            // 新增
            sysDept = new SysDept();
            sysDept.setDeptName(params.getDeptName());
            sysDept.setParentNo(parentNo == null ? "" : parentNo);
            String maxDeptNo = sysDeptMapper.getDeptNoByParentNo(sysDept.getParentNo());
            String maxDeptNo2 = String.format("%03d", Integer.parseInt(null == maxDeptNo ? "0" : maxDeptNo) + 1);
            String deptNoParam = "root".equals(parentNo) ? "" : parentNo;
            sysDept.setDeptNo(deptNoParam + maxDeptNo2);
            sysDeptMapper.insert(sysDept);
        }
    }

    public void delete(Integer id) {
        SysDept sysDept = sysDeptMapper.selectById(id);
        if (null == sysDept.getParentNo() || "".equals(sysDept.getParentNo())) {
            throw new ApiException(ApiResult.ERROR, "根部门不允许删除");
        }
        sysDeptMapper.deleteById(id);
    }
}
