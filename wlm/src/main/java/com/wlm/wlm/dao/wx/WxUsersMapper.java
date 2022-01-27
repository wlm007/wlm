package com.wlm.wlm.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.model.wx.WxUsers;

/**
 * 微信用户
 * @author wuliming
 */
public interface WxUsersMapper extends BaseMapper<WxUsers> {

    IPage<WxUsers> findWxUsers(Page<WxUsers> page);

    IPage<WxUsers> getBlackList(Page<WxUsers> page);
}