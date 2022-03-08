package com.wlm.wlm.model.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 图文消息
 * @author wuliming
 * @date 2022/1/10 14:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ArticlesMessage extends BaseMessage{

    private int ArticleCount;

    private List<Articles> Articles;
}
