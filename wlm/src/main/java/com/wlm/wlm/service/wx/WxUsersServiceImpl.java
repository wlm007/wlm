package com.wlm.wlm.service.wx;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlm.wlm.config.ApiException;
import com.wlm.wlm.config.ApiResult;
import com.wlm.wlm.config.PageInfoResult;
import com.wlm.wlm.dao.wx.WxUsersMapper;
import com.wlm.wlm.dao.wx.WxUsersSignMapper;
import com.wlm.wlm.model.wx.WxUsers;
import com.wlm.wlm.model.wx.WxUsersSign;
import com.wlm.wlm.params.wx.SignToUserParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 微信关注用户service
 * @author wuliming
 * @date 2022/1/20 14:41
 */
@Service
public class WxUsersServiceImpl {


    private WxServiceImpl wxService;

    @Autowired
    public void setWxService(WxServiceImpl wxService) {
        this.wxService = wxService;
    }

    private WxUsersMapper usersMapper;

    @Autowired
    public void setUsersMapper(WxUsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    private WxUsersSignMapper usersSignMapper;

    @Autowired
    public void setUsersSignMapper(WxUsersSignMapper usersSignMapper) {
        this.usersSignMapper = usersSignMapper;
    }

    String errcode = "errcode";

    public PageInfoResult<List<WxUsers>> getUserList(Integer pageNo, Integer pageSize) {
        Page<WxUsers> page = new Page<>(pageNo, pageSize);
        IPage<WxUsers> result = usersMapper.findWxUsers(page);
        return new PageInfoResult<>(result.getTotal(), result.getRecords());
    }

    public JSONObject getUserListByWx(String openId) {
        // 获取用户列表url
        String getUserListUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
        String url = getUserListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken()).replace("NEXT_OPENID", openId == null ? "" : openId);
        return wxService.httpsRequest(url, wxService.getGET(), null);
    }

    public void saveUser(String fromUser) {
        // 获取用户基本信息url  500000 次/天
        String accessUserUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        String url = accessUserUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken()).replace("OPENID", fromUser);
        JSONObject userInfo = wxService.httpsRequest(url, wxService.getGET(), null);
        WxUsers users = usersMapper.selectOne(new LambdaQueryWrapper<WxUsers>().eq(WxUsers::getOpenid, fromUser));
        if (null == users) {
            usersMapper.insert(new WxUsers(userInfo));
        } else {
            Integer id = users.getId();
            users = new WxUsers(userInfo);
            users.setId(id);
            usersMapper.updateById(users);
        }
    }

    public void setUserRemark(String openId, String remark) {
        String remarkUrl = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
        String url = remarkUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{" +
                "\"openid\":\"" +openId+ "\"," +
                "\"remark\":\""+remark+"\"" +
                "}";
        wxService.httpsRequest(url, wxService.getPOST(), outStr);
        saveUser(openId);
    }

    public void addUserSign(String signName) {
        String addUserSignUrl = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
        String url = addUserSignUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"tag\":{\"name\":\""+signName+"\"}}";
        JSONObject result = wxService.httpsRequest(url, wxService.getPOST(), outStr);
        // 将标签保存到数据库
        WxUsersSign usersSign = result.getObject("tag", WxUsersSign.class);
        usersSignMapper.save(usersSign);
    }

    public PageInfoResult<List<WxUsersSign>> userSignList(Integer pageNo, Integer pageSize) {
        Page<WxUsersSign> page = new Page<>(pageNo, pageSize);
        IPage<WxUsersSign> result = usersSignMapper.userSignList(page);
        return new PageInfoResult<>(result.getTotal(), result.getRecords());
    }

    public JSONObject getUserSignListByWx() {
        String getUserSignListByWxUrl = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
        String url = getUserSignListByWxUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        return wxService.httpsRequest(url, wxService.getGET(), null);
    }

    public void editUserSign(Integer id, String signName) {
        String editUserSignUrl = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";
        String url = editUserSignUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"tag\":{\"id\":"+id+",\"name\":\""+signName+"\"}}";
        JSONObject result = wxService.httpsRequest(url, wxService.getPOST(), outStr);
        if (result.getInteger(errcode) != 0) {
            throw new ApiException(ApiResult.ERROR, result.getString("errmsg"));
        }
        usersSignMapper.updateNameById(id, signName);
    }

    public void deleteUserSign(Integer id) {
        String deleteUserSignUrl = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";
        String url = deleteUserSignUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"tag\":{\"id\":"+id+"}}";
        JSONObject result = wxService.httpsRequest(url, wxService.getPOST(), outStr);
        if (result.getInteger(errcode) != 0) {
            throw new ApiException(ApiResult.ERROR, result.getString("errmsg"));
        }
        usersSignMapper.deleteById(id);
    }

    public List<WxUsersSign> getUsersSignList(String openid) {
        String getUsersSignListUrl = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";
        String url = getUsersSignListUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"openid\":\""+openid+"\"}";
        JSONObject result = wxService.httpsRequest(url, wxService.getPOST(), outStr);
        Integer[] signIds = result.getObject("tagid_list", Integer[].class);
        if (signIds.length <= 0) {
            return new ArrayList<>();
        }
        return usersSignMapper.selectBatchIds(Arrays.asList(signIds));
    }

    public List<WxUsers> getUserListBySign(Integer signId, String nextOpenid) {
        String getUserListBySignUrl = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";
        String url = getUserListBySignUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = "{\"tagid\":"+signId+",\"next_openid\":"+nextOpenid+"}";
        JSONObject result = wxService.httpsRequest(url, wxService.getPOST(), outStr);
        System.out.println(result.getString("data"));
        return new ArrayList<>();
    }

    public void signToUser(SignToUserParams params) {
        String signToUserUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
        signUser(params, signToUserUrl);
    }

    public void cancelSignToUser(SignToUserParams params) {
        String cancelSignToUserUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
        signUser(params, cancelSignToUserUrl);
    }

    private void signUser(SignToUserParams params, String signUrl) {
        if (params.getOpenid_list().size() > 50) {
            throw new ApiException(ApiResult.ERROR, "每次传入的openid列表个数不能超过50个");
        }
        String url = signUrl.replace(wxService.getACCESS_TOKEN(), wxService.getAccessToken());
        String outStr = JSONObject.toJSONString(params);
        JSONObject result = wxService.httpsRequest(url, wxService.getPOST(), outStr);
        if (result.getInteger(errcode) != 0) {
            throw new ApiException(ApiResult.ERROR, result.getString("errmsg"));
        }
    }
}
