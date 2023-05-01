package com.spring.onedaythink.haruchat.Controller;

import com.spring.onedaythink.haruchat.service.HaruChatService;
import com.spring.onedaythink.haruchat.vo.HaruChat;
import com.spring.onedaythink.haruchat.vo.HaruChatMessage;
import com.spring.onedaythink.haruchat.vo.HaruChatMessageDetail;
import com.spring.onedaythink.haruchat.vo.HaruChatRoom;
import com.spring.onedaythink.user.vo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/haruchat")
public class HaruChatController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private HaruChatService haruChatService;

    // 하루봇 랜덤 조회
    @GetMapping
    public ResponseEntity getRandomHaruBot() {
        List<HaruChat> haruBotList = haruChatService.getRandomHaruBot();
        log.debug("getRandomHaruBot");
        return ResponseEntity.ok(haruBotList);
    }

    // 채팅할 하루봇 선택 후 채팅 시작
    @PostMapping(value="/{userNo}")
    public ResponseEntity startHaruChat(@RequestBody HaruChat[] haruChats, @PathVariable int userNo){
        log.debug("startHaruChat");
        User user = User.builder().userNo(userNo).build();
        int result = haruChatService.createHaruChatRoom(user);
        HaruChatRoom haruChatRoom = haruChatService.getHaruChatRoomInfo();
        int resultsum = haruChatService.selectedHaruBot(haruChats, haruChatRoom);
        return ResponseEntity.ok(resultsum);
    }

    // 페르소나봇들과의 대화 - 채팅방 전체 조회
    @GetMapping(value = "/haruchatall")
    public ResponseEntity<Object> selectAllharuChatRoom(){
        List<HaruChatRoom> result = haruChatService.selectAllharuChatRoom();
        return ResponseEntity.ok(result);
    }

    // 페르소나봇들과의 대화 - 채팅방 개별 조회 GET
    @GetMapping(value = "/{userNo}")
    public ResponseEntity<Object> selectOneHaruChatRoom(@PathVariable int userNo) {

        HaruChatRoom haruChatRoom = haruChatService.selectOneHaruChatRoom(HaruChatRoom.builder().userNo(userNo).build());
        if (haruChatRoom != null) {
            return ResponseEntity.ok(haruChatRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 페르소나봇들과의 대화 - 마지막 메시지 조회
    @GetMapping(value="/lastchat/{chatRoomNo}")
    public ResponseEntity getLastMessage(@PathVariable int chatRoomNo){
        HaruChatMessage lastMessage = haruChatService.getLastMessage(HaruChatRoom.builder().chatRoomNo(chatRoomNo).build());
        log.debug("getLastMessage");
        return ResponseEntity.ok(lastMessage);
    }

    // 페르소나봇들과의 대화 - 메세지 생성
    @PostMapping
    public ResponseEntity<Object> insertHaruChatMsg(@RequestBody HaruChatMessage haruChatMessage) {
        int result = haruChatService.insertHaruChatMsg(haruChatMessage);
        return ResponseEntity.ok(result);
    }

    // 페르소나봇들과의 대화 - 나가기
    @PostMapping(value="{chatRoomNo}/close")
    public ResponseEntity closeHaruChatRoom(@PathVariable int chatRoomNo){
        log.debug("closeHaruChatRoom");
        int result = haruChatService.closeHaruChatRoom(HaruChatRoom.builder().chatRoomNo(chatRoomNo).build());
        return ResponseEntity.ok(result);
    }

}



