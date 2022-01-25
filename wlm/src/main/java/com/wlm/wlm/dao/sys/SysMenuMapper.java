package com.wlm.wlm.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wlm.wlm.model.sys.SysMenu;

/**
 * 系统菜单
 * @author wuliming
 * @date 2021-10-19
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    void deleteAllById(Integer id);
}