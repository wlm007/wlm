package com.wlm.wlm.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wlm.wlm.model.wx.WxUsersBindSign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuliming
 */
public interface WxUsersBindSignMapper extends BaseMapper<WxUsersBindSign> {

    void batchInsert(@Param("bindSignList") List<WxUsersBindSign> bindSignList);

    void batchDelete(@Param("openidList") List<String> openidList);
}