package com.wlm.wlm.service.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.dao.sys.SysMenuMapper;
import com.wlm.wlm.model.sys.SysMenu;
import com.wlm.wlm.params.sysMenu.SysMenuSaveParams;
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
    private SysMenuMapper sysMenuMapper;

    public List<SysMenuVo> list() {
        return sysMenuMapper.selectList(new LambdaQueryWrapper<>())
                .stream().map(SysMenuVo::new).collect(Collectors.toList());
    }

    public void save(SysMenuSaveParams params) {
        Integer id = params.getId();
        SysMenu sysMenu;
        // 更新
        if (null != id && id > 0) {
            sysMenu = sysMenuMapper.selectById(id);
            if (null == sysMenu) {
                throw new ApiException(ApiResult.ERROR, "该菜单不存在");
            }
            sysMenu = params.getMenu();
            sysMenuMapper.updateById(sysMenu);
        } else {
            // 新增
            sysMenu = params.getMenu();
            sysMenuMapper.insert(sysMenu);
        }
    }

    public void delete(Integer id) {
        // 将该菜单及子菜单都删除
        sysMenuMapper.deleteAllById(id);
    }
}
