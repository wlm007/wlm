package com.wlm.wlm.model.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信素材实体类
 * wx_material
 * @author wuliming
 */
@Data
public class WxMaterial implements Serializable {

    private static final long serialVersionUID = 4801920807967504444L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 素材类型 图片（image）、语音（voice）、视频（video）和缩略图（thumb）'
     */
    private String type;

    /**
     * 媒体id
     */
    private String mediaId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件路径
     */
    private String url;

    /**
     * 更新时间
     */
    private Long updateTime;
}