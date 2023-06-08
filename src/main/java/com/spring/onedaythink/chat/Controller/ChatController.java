package com.spring.onedaythink.chat.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.onedaythink.chat.service.ChatService;
import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatMessageDetail;
import com.spring.onedaythink.chat.vo.ChatRoom;
import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import com.spring.onedaythink.user.vo.User;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "api/v1/")
@RequiredArgsConstructor
public class ChatController {

    private Logger log = LogManager.getLogger("case3");

    @Autowired
    private ChatService chatService;

    private final Job job;  // tutorialJob
    private final JobLauncher jobLauncher;


    // 5초마다 실행
//    @Scheduled(fixedDelay = 60 * 1000L)
//    public void executeJob () {
//        try {
//            jobLauncher.run(
//                    job,
//                    new JobParametersBuilder()
//                            .addString("datetime", LocalDateTime.now().toString())
//                            .toJobParameters()  // job parameter 설정
//            );
//        } catch (JobExecutionException ex) {
//            log.debug(ex.getMessage());
//            ex.printStackTrace();
//        }
//    }
    @GetMapping(value="chat/rooms/{chatRoomNo}/messagesAll")
    public ResponseEntity<Object> getLastMessageAll(@PathVariable int chatRoomNo){
        log.debug("getLastMessage");
        return ResponseEntity.ok(chatService.getChatMessageDetails(ChatRoomDetail.builder().chatRoomNo(chatRoomNo).build()));
    }

    // 마지막 메시지 조회
    @GetMapping(value="chat/rooms/{chatRoomNo}/messages")
    public ResponseEntity getLastMessage(@PathVariable int chatRoomNo){
        log.debug("getLastMessage");
        ChatMessage lastMessage = chatService.getLastMessage(ChatRoom.builder().chatRoomNo(chatRoomNo).build());
        return ResponseEntity.ok(lastMessage);
    }

    // 해당 사용자가 가지고 있는 채팅방 목록 조회
    @GetMapping(value = "chat/rooms/{userNo}")
    public ResponseEntity<Object> getCharRooms(@PathVariable int userNo) {
        log.debug("# All Chat Rooms");
        List<ChatRoomDetail> chatRoomDetails = chatService.getChatRooms(ChatRoomDetail.builder().findUserNo(userNo).build());
        return ResponseEntity.ok(chatRoomDetails);
    }

    // 채팅 종료
    @GetMapping(value="chat/rooms/{chatRoomNo}/close")
    public ResponseEntity closeChatRoom(@PathVariable int chatRoomNo){
        log.debug("closeChatRoom");
        int result = chatService.closeChatRoom(ChatRoom.builder().chatRoomNo(chatRoomNo).build());
        return ResponseEntity.ok(result);
    }

    // 채팅방 개설
    @PostMapping(value = "chat/rooms")
    public ResponseEntity<Object> addChatRoom(@RequestBody ChatRoom chatRoom){
        log.debug("# Create Chat Room , name: " + chatRoom.toString());
        Map<String, Object> map = chatService.addChatRoom(chatRoom);
        return ResponseEntity.ok(map);
    }

    // 해당 사용자가 가지고 있는 채팅방 목록 조회
    @GetMapping(value = "chat/rooms/messages/{chatRoomNo}")
    public ResponseEntity<Object> getCharMessageDetails(@PathVariable int chatRoomNo) {
        log.debug("# All Chat Rooms message");
        List<ChatMessageDetail> msgList = chatService.getChatMessageDetails(ChatRoomDetail.builder().chatRoomNo(chatRoomNo).build());
        return ResponseEntity.ok(msgList);
    }

    // 전체 채팅방 조회 (관리자용)
    @GetMapping(value = "admin/chatRoomsAdmin")
    public ResponseEntity<Object> getChatRoomsAdmin() {
        List<ChatRoom> chatRoom = chatService.getChatRoomsAdmin(null);
        return ResponseEntity.ok(chatRoom);
    }
}