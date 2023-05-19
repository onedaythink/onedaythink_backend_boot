package com.spring.onedaythink.haruchat.Controller;

import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.service.ChatBotService;
import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfo;
import com.spring.onedaythink.haruchat.vo.SelectedHaruInfoDetail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //하루봇 랜덤 조회
    @GetMapping
    public ResponseEntity getRandomHaruBot() {
        List<HaruChat> haruBotList = chatBotService.getRandomHaruBot();
        log.debug("getRandomHaruBot");
        return ResponseEntity.ok(haruBotList);
    }

    // 채팅방 개설 : 채팅방 번호 부여, 페르소나 챗봇 첫 의견 받아오기
    @PostMapping(value = "/enter")
    public ResponseEntity<Object> receiveFirstMsgFromChatGPT(@RequestBody SelectedHaruInfo selectedHaruInfo) throws ExecutionException, InterruptedException {
        log.debug("receiveFirstMsgFromChatGPT");
        List<HaruChatMessage> list = chatBotService.getFirstMsgFromChatGPT(selectedHaruInfo);
        return ResponseEntity.ok(list);
    }

    // 채팅 진행 중 페르소나 챗봇 답변 받아오기
    @PostMapping(value = "/ongoingchat")
    public ResponseEntity<Object> receiveMsgFromChatGPT(@RequestBody SelectedHaruInfoDetail selectedHaruInfoDetail){
        log.debug("receiveMsgFromChatGPT");
        List<HaruChatMessage> list = chatBotService.getMsgFromChatGPT(selectedHaruInfoDetail);
        return ResponseEntity.ok(list);
    }

    //하루봇 랜덤 조회
    @GetMapping
    public ResponseEntity getRandomHaruBot() {
        List<HaruChat> haruBotList = chatBotService.getRandomHaruBot();
        log.debug("getRandomHaruBot");
        return ResponseEntity.ok(haruBotList);
    }
}
