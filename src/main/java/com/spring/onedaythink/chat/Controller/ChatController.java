package com.spring.onedaythink.chat.Controller;

import com.spring.onedaythink.chat.service.ChatService;
import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping(value = "api/v1/chat")
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

    // 해당 사용자가 가지고 있는 채팅방 목록 조회
    @GetMapping(value = "/rooms/{userNp}")
    public ResponseEntity<Object> getCharRooms(@PathVariable int userNo){

        log.debug("# All Chat Rooms");

        return ResponseEntity.ok(null);
    }

    //채팅방 개설
    @PostMapping(value = "/room")
    public ResponseEntity<Object> addChatRoom(@RequestBody ChatRoom chatRoom){
        log.debug("# Create Chat Room , name: " + chatRoom.toString());

        return ResponseEntity.ok(null);
    }

    //채팅방 요청 승인/거절 관리
//    @PostMapping("/room/process")
//    public ResponseEntity<Object> (String roomId, Model model){
//
//        log.info("# get Chat Room, roomID : " + roomId);
//
//        return ResponseEntity.ok(null);
//    }

}
