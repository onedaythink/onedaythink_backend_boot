package com.spring.onedaythink.haruchat.service;

import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGPTServiceImpl implements ChatGPTService{

    private Logger log = LogManager.getLogger("case3");

    @Value("")
    private String openaiApiKey;
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    @Autowired
    private HaruChatMapper haruChatMapper;

    @Override
    public HaruChatMessage getChatGPTResponse(HaruChatMessage haruChatMessage) {

        log.debug("getChatGPTResponse"+openaiApiKey);
        // API 호출
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+openaiApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 500);
        ArrayList<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "assistant");
        message.put("content", haruChatMessage.getChatMsgContent());
        messages.add(message);
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // RESPONSE 수신
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(ENDPOINT, requestEntity, Map.class);

        Map<String, Object> responseBody = response.getBody();

        String created = responseBody.get("created").toString();
        List<Map<String, Object>> completions = (List<Map<String, Object>>) responseBody.get("choices");
        Map<String, Object> firstCompletion = completions.get(0);
        String generatedText = (String)((Map<String, Object>) firstCompletion.get("message")).get("content");
        log.debug(generatedText);

        // 받아온 메시지 insert
        HaruChatMessage haruChatMessageResponse = new HaruChatMessage();
        haruChatMessageResponse.setChatRoomNo(haruChatMessage.getChatRoomNo());
        haruChatMessageResponse.setChatMsgContent(generatedText);
        haruChatMessageResponse.setChatCreateAt(created);
        haruChatMapper.insertHaruChatMsg(haruChatMessageResponse);

        return haruChatMessageResponse;
    }
}
