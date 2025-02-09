package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.dto.DeepSeekRequest;
import com.tencent.wxcloudrun.dto.DeepSeekResponse;
import com.tencent.wxcloudrun.dto.WeChatResponse;
import com.tencent.wxcloudrun.model.WeChatMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import java.security.MessageDigest;
import java.util.Arrays;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

    @Value("${wechat.token}")
    private String wechatToken;

    @Value("${deepseek.api.key}")
    private String deepseekApiKey;

    private final String DEEPSEEK_API_URL = "https://api.deepseek.com/chat/completions";

    @GetMapping
    public String validate(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {
        if (checkSignature(signature, timestamp, nonce, wechatToken)) {
            return echostr;
        } else {
            return "验证失败";
        }
    }

    @PostMapping
    public ResponseEntity<String> handleMessage(@RequestBody WeChatMessage message) {
        // 调用 DeepSeek API
        RestTemplate restTemplate = new RestTemplate();
        DeepSeekRequest deepSeekRequest = new DeepSeekRequest(message.getContent());
        DeepSeekResponse deepSeekResponse = restTemplate.postForObject(
                DEEPSEEK_API_URL,
                deepSeekRequest,
                DeepSeekResponse.class,
                deepseekApiKey
        );

        // 返回响应给微信
        WeChatResponse response = new WeChatResponse();
        response.setToUserName(message.getFromUserName());
        response.setFromUserName(message.getToUserName());
        response.setCreateTime(System.currentTimeMillis() / 1000);
        response.setMsgType("text");
        response.setContent(deepSeekResponse != null ? deepSeekResponse.getResponse() : "服务繁忙，请稍后再试");

        return new ResponseEntity<>(response.toXml(), HttpStatus.OK);
    }

    private boolean checkSignature(String signature, String timestamp, String nonce, String token) {
        String[] arr = new String[]{timestamp, nonce, token};
        Arrays.sort(arr);
        String combined = String.join("", arr);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(combined.getBytes());
            String calculatedSignature = bytesToHex(digest);
            return calculatedSignature.equals(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}