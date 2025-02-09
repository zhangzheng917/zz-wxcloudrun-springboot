package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class DeepSeekRequest {
    private String message;

    public DeepSeekRequest(String message) {
        this.message = message;
    }
}