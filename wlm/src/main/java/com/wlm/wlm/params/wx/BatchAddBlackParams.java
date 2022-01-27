package com.wlm.wlm.params.wx;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wuliming
 * @date 2022/1/25 11:18
 */
@Data
public class BatchAddBlackParams {

    @NotNull(message = "用户id集合不能为空")
    @Size(max = 20, min = 1, message = "用户openid个数不能小于1和大于20")
    private List<String> openid_list;
}
