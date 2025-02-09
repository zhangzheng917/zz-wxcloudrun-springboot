package com.tencent.wxcloudrun.dto;

import lombok.Data;
import java.util.List;

@Data
public class DeepSeekRequest {
    private String model; // 模型名称（如 "deepseek-chat"）
    private List<Message> messages; // 消息列表

    @Data
    public static class Message {
        private String role; // 角色（如 "user" 或 "assistant"）
        private String content; // 消息内容
    }
}