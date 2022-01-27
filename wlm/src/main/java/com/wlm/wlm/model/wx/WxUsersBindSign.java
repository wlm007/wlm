package com.wlm.wlm.model.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * wx_users_bind_sign
 * @author wuliming
 */
@Data
@NoArgsConstructor
public class WxUsersBindSign implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户openid
     */
    private String openid;

    /**
     * 标签id
     */
    private Integer tagId;

    private static final long serialVersionUID = 1L;

    public WxUsersBindSign(String openid, Integer tagId) {
        this.openid = openid;
        this.tagId = tagId;
    }
}