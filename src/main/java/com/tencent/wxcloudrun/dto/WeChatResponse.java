package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class WeChatResponse {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;

    public String toXml() {
        return "<xml>" +
                "<ToUserName><![CDATA[" + ToUserName + "]]></ToUserName>" +
                "<FromUserName><![CDATA[" + FromUserName + "]]></FromUserName>" +
                "<CreateTime>" + CreateTime + "</CreateTime>" +
                "<MsgType><![CDATA[" + MsgType + "]]></MsgType>" +
                "<Content><![CDATA[" + Content + "]]></Content>" +
                "</xml>";
    }
}