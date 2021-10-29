package com.wlm.wlm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wlm.wlm.dao.SysDeptMapper;
import com.wlm.wlm.model.SysDept;
import com.wlm.wlm.params.sysDept.SysDeptListParams;
import com.wlm.wlm.util.StringUtils;
import com.wlm.wlm.vo.SysDeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<SysDept> deptList = sysDeptMapper.selectList(new LambdaQueryWrapper<>());
        if (StringUtils.isNotEmpty(params.getDeptName())) {
            deptList = sysDeptMapper.selectList(new LambdaQueryWrapper<SysDept>().like(SysDept::getDeptName, params.getDeptName().trim()));
        }
        return deptList.isEmpty() ? new ArrayList<>() : deptList.stream().map(SysDeptVo::new).collect(Collectors.toList());
    }
}
