package com.spring.onedaythink.haruchat.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.onedaythink.haruchat.service.ChatGPTService;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/v1/haruchat")
public class ChatGPTController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping("/chatgpt/response")
    public ResponseEntity<Object> receiveMsgFromChatGPT (@RequestBody HaruChatMessage haruChatMessage){
        log.debug("getChatGPTResponse");
        HaruChatMessage haruChatMessageResponse
                = chatGPTService.getChatGPTResponse(haruChatMessage);
        return ResponseEntity.ok(haruChatMessageResponse);
    }

    @PostMapping("/papago/test")
    public ResponseEntity<Object> getTranslatedPrompt (HaruChatMessage haruChatMessage) throws JsonProcessingException {
        log.debug("getTranslatedPrompt");
        String prompt = chatGPTService.getTranslatedPrompt(haruChatMessage);
        return ResponseEntity.ok(prompt);
    }








}
