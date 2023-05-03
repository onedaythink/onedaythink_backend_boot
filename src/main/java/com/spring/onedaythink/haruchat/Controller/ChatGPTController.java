package com.spring.onedaythink.haruchat.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.onedaythink.haruchat.service.ChatGPTService;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/haruchat")
public class ChatGPTController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping("/chatgpt/response")
    public ResponseEntity<Object> receiveMsgFromChatGPT (@RequestBody SelectedHaruInfo selectedHaruInfo) throws JsonProcessingException {
        log.debug("getChatGPTResponse");
        List<HaruChatMessage> list
                = chatGPTService.getChatGPTResponse(selectedHaruInfo);

        return ResponseEntity.ok(list);
    }










}
