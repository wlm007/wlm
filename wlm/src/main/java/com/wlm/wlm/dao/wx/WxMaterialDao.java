package com.wlm.wlm.dao.wx;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.model.wx.WxMaterial;
import org.apache.ibatis.annotations.Param;

/**
 * 微信素材 dao
 * @author wuliming
 */
public interface WxMaterialDao extends BaseMapper<WxMaterial> {

    /**
     * 分页获取微信素材
     * @param type 类型
     * @param page 分页参数
     * @return 微信素材列表
     */
    IPage<WxMaterial> getMaterialList(@Param("type") String type, Page<WxMaterial> page);
}