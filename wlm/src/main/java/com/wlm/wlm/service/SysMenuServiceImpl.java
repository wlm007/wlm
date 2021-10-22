package com.wlm.wlm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wlm.wlm.dao.SysMenuDao;
import com.wlm.wlm.vo.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单service
 * @author wuliming
 * @date 2021/10/19 14:32
 */
@Service
public class SysMenuServiceImpl {
    @Autowired
    private SysMenuDao sysMenuDao;

    public List<SysMenuVo> list() {
        return sysMenuDao.selectList(new LambdaQueryWrapper<>())
                .stream().map(SysMenuVo::new).collect(Collectors.toList());
    }
}
