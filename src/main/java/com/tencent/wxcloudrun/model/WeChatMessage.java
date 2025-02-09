package com.tencent.wxcloudrun.model;

import lombok.Data;

@Data
public class WeChatMessage {
    private String toUserName;
    private String fromUserName;
    private Long createTime;
    private String msgType;
    private String content;
    private String msgId;
}