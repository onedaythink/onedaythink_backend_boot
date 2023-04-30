package com.spring.onedaythink.haruchat.Controller;

import com.spring.onedaythink.haruchat.service.HaruChatService;
import com.spring.onedaythink.haruchat.vo.HaruChat;
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



}
