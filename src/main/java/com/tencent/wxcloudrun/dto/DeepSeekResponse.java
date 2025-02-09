package com.tencent.wxcloudrun.dto;

import lombok.Data;
import java.util.List;

@Data
public class DeepSeekResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Message message;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }

    public String getResponse() {
        if (choices != null && !choices.isEmpty()) {
            return choices.get(0).getMessage().getContent();
        }
        return null;
    }
}