package com.spring.onedaythink.haruchat.Controller;

import com.spring.onedaythink.haruchat.service.ChatGPTService;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
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
    public ResponseEntity<Object> getChatGPTResponse(@RequestBody HaruChatMessage haruChatMessage){
        log.debug("getChatGPTResponse");
        HaruChatMessage haruChatMessageResponse
                = chatGPTService.getChatGPTResponse(haruChatMessage);
        return ResponseEntity.ok(haruChatMessageResponse);
    }








}
