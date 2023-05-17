package com.spring.onedaythink.haruchat.Controller;


import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.service.ChatBotService;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfo;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfoDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping(value="api/v1/haruchat")
public class ChatBotController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatBotService chatBotService;

    @Autowired
    private HaruChatMapper haruChatMapper;

    @PostMapping("/enter")
    public ResponseEntity<Object> receiveFirstMsgFromChatGPT(@RequestBody SelectedHaruInfo selectedHaruInfo) throws ExecutionException, InterruptedException {
        log.debug("receiveFirstMsgFromChatGPT");
        List<HaruChatMessage> list = chatBotService.getFirstMsgFromChatGPT(selectedHaruInfo);

        return ResponseEntity.ok(list);
    }

    @PostMapping("/ongoingchat")
    public ResponseEntity<Object> receiveMsgFromChatGPT(@RequestBody SelectedHaruInfoDetail selectedHaruInfoDetail){
        log.debug("receiveMsgFromChatGPT");
        List<HaruChatMessage> list = chatBotService.getMsgFromChatGPT(selectedHaruInfoDetail);

        return ResponseEntity.ok(list);
    }

}
