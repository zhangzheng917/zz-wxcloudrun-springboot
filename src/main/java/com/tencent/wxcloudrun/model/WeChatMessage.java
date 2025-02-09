package com.tencent.wxcloudrun.model;

import lombok.Data;

@Data
public class WeChatMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
}