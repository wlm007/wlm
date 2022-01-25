package com.wlm.wlm.params.wx;

import lombok.Data;

import java.util.List;

/**
 * @author wuliming
 * @date 2022/1/24 14:45
 */
@Data
public class SignToUserParams {

    private Integer tagid;

    private List<String> openid_list;
}
