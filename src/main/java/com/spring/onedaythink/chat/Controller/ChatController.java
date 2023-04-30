package com.spring.onedaythink.chat.Controller;

import com.spring.onedaythink.chat.service.ChatService;
import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "chat")
public class ChatController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatService chatService;

    // 마지막 메시지 조회
    @GetMapping(value="/{chatRoomNo}")
    public ResponseEntity getLastMessage(@PathVariable int chatRoomNo){
        ChatRoom chatRoom = ChatRoom.builder().chatRoomNo(chatRoomNo).build();
        ChatMessage lastMessage = chatService.getLastMessage(chatRoom);
        log.debug("getLastMessage");
        return ResponseEntity.ok(lastMessage);
    }

    // 채팅 종료




}
