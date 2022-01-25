package com.wlm.wlm.model.wx;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * wx_users
 * @author wuliming
 */
@Data
@NoArgsConstructor
public class WxUsers implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息
     */
    private Integer subscribe;

    /**
     * 用户的标识，对当前公众号唯一
     */
    private String openid;

    /**
     * 用户的语言，简体中文为zh_CN
     */
    private String language;

    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    private Long subscribeTime;

    /**
     * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    private String unionid;

    /**
     * 公众号运营者对粉丝的备注
     */
    private String remark;

    /**
     * 用户所在的分组ID（兼容旧的用户分组接口）
     */
    private Integer groupid;

    private String tagidList;

    /**
     * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，
     * ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，
     * ADD_SCENE_PROFILE_CARD 名片分享，
     * ADD_SCENE_QR_CODE 扫描二维码，
     * ADD_SCENE_PROFILE_LINK 图文页内名称点击，
     * ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，
     * ADD_SCENE_PAID 支付后关注，
     * ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，
     * ADD_SCENE_REPRINT 他人转载 ,
     * ADD_SCENE_LIVESTREAM 视频号直播，
     * ADD_SCENE_CHANNELS 视频号 ,
     * ADD_SCENE_OTHERS 其他
     */
    private String subscribeScene;

    /**
     * 二维码扫码场景（开发者自定义）
     */
    private Integer qrScene;

    /**
     * 二维码扫码场景（开发者自定义）
     */
    private String qrSceneStr;

    private static final long serialVersionUID = 1L;

    public WxUsers(JSONObject jsonObject) {
        this.subscribe = jsonObject.getInteger("subscribe");
        this.openid = jsonObject.getString("openid");
        this.language = jsonObject.getString("language");
        this.subscribeTime = jsonObject.getLong("subscribe_time");
        this.unionid = jsonObject.getString("unionid");
        this.remark = jsonObject.getString("remark");
        this.groupid = jsonObject.getInteger("groupid");
        // 用户标签在微信后台为 [""] 所以截取掉中括号
        String tagList = jsonObject.getString("tagid_list");
        this.tagidList = tagList.substring(1, tagList.length() - 1);
        this.subscribeScene = jsonObject.getString("subscribe_scene");
        this.qrScene = jsonObject.getInteger("qr_scene");
        this.qrSceneStr = jsonObject.getString("qr_scene_str");
    }
}