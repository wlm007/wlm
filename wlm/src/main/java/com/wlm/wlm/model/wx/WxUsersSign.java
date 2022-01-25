package com.wlm.wlm.model.wx;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * wx_users_sign
 * @author wuliming
 */
@Data
public class WxUsersSign implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Integer id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 此标签下粉丝数
     */
    private Integer count;

    private static final long serialVersionUID = 1L;
}