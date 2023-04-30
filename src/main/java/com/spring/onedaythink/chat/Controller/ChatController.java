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
@RequestMapping(value = "api/v1")
public class ChatController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatService chatService;

    // 마지막 메시지 조회
    @GetMapping(value="chat/{chatRoomNo}")
    public ResponseEntity getLastMessage(@PathVariable int chatRoomNo){
        ChatMessage lastMessage = chatService.getLastMessage(ChatRoom.builder().chatRoomNo(chatRoomNo).build());
        log.debug("getLastMessage");
        return ResponseEntity.ok(lastMessage);
    }

    // 채팅 종료
    @PostMapping(value="chat/{chatRoomNo}/close")
    public ResponseEntity closeChatRoom(@PathVariable int chatRoomNo){
        log.debug("closeChatRoom");
        int result = chatService.closeChatRoom(ChatRoom.builder().chatRoomNo(chatRoomNo).build());
        return ResponseEntity.ok(result);
    }




}
