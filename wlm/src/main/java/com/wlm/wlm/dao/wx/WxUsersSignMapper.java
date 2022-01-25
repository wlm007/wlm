package com.wlm.wlm.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.model.wx.WxUsersSign;
import org.apache.ibatis.annotations.Param;

/**
 * @author wuliming
 */
public interface WxUsersSignMapper extends BaseMapper<WxUsersSign> {

    void save(@Param("usersSign") WxUsersSign usersSign);

    IPage<WxUsersSign> userSignList(Page<WxUsersSign> page);

    void updateNameById(@Param("id") Integer id, @Param("signName") String signName);
}