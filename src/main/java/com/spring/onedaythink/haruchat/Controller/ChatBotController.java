package com.spring.onedaythink.haruchat.Controller;

import com.spring.onedaythink.haruchat.mapper.HaruChatMapper;
import com.spring.onedaythink.haruchat.service.ChatBotService;
import com.spring.onedaythink.haruchat.vo.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping(value="api/v1/haruchat")
public class ChatBotController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatBotService chatBotService;


    //하루봇 랜덤 조회
    @GetMapping
    public ResponseEntity getRandomHaruBot() {
        List<HaruChat> haruBotList = chatBotService.getRandomHaruBot();
        log.debug("getRandomHaruBot");
        return ResponseEntity.ok(haruBotList);
    }

    // 해당 사용자가 가지고 있는 채팅방 목록 조회
    @GetMapping("/rooms/{userNo}")
    public ResponseEntity<Object> getChatRoomsByUserNo(@PathVariable int userNo){
        log.debug("getChatRoomsByUserNo");
        List<HaruChatRoomDetail> haruChatRoomDetails = chatBotService.getChatRoomsByUserNo(HaruChatRoomDetail.builder().userNo(userNo).build());
        return ResponseEntity.ok(haruChatRoomDetails);
    }

    // 채팅방 재입장시 이전 대화 기록 조회
    @PostMapping("/rooms/messages")
    public ResponseEntity<Object> getChatMessagesByChatRoomNo(@RequestBody HaruChatRoom haruChatRoom){
        log.debug("getChatMessageByChatRoomNo");
        log.debug(haruChatRoom);
//        Map<String, Map<String, String>> haruChatMessageDetailMap = chatBotService.getChatMessagesByChatRoomNo(haruChatRoom);
        List<Map<String, String>> haruChatMessageDetailMap = chatBotService.getChatMessagesByChatRoomNo(haruChatRoom);
        log.debug(haruChatMessageDetailMap);
        return ResponseEntity.ok(haruChatMessageDetailMap);
    }


    // 채팅 종료
    @GetMapping(value="/rooms/{chatRoomNo}/close")
    public ResponseEntity<Object> closeChatRoom(@PathVariable int chatRoomNo){
        log.debug("closeChatRoom");
        int result = chatBotService.closeChatRoom(HaruChatRoom.builder().chatRoomNo(chatRoomNo).build());
        return ResponseEntity.ok(result);
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
    public void receiveMsgFromChatGPT(@RequestBody SelectedHaruInfoDetail selectedHaruInfoDetail){
        log.debug("receiveMsgFromChatGPT");
        List<HaruChatMessage> list = chatBotService.getMsgFromChatGPT(selectedHaruInfoDetail);
        log.debug(list);
        chatBotService.sendMessage(list);
    }

    // 채팅방 입장 시 해당 채팅방에 속해 있는 하루의 정보를 조회
    @GetMapping(value = "rooms/{chatRoomNo}/selected-haru")
    public ResponseEntity<Object> getSelectedChar(@PathVariable int chatRoomNo) {
        List<SelectedChar> selectedChars = chatBotService.getSelectedChar(SelectedChar.builder().chatRoomNo(chatRoomNo).build());
        return ResponseEntity.ok(selectedChars);
    }
}
