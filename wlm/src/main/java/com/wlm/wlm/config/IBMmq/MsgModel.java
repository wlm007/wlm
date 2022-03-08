package com.wlm.wlm.config.IBMmq;

import java.io.Serializable;

/**
 * @author wuliming
 * @date 2022/2/11 11:30
 */
public class MsgModel implements Serializable {

    private static final long serialVersionUID = 1103643834064932592L;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
