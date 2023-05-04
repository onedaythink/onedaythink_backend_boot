package com.spring.onedaythink.chat.Controller;


import com.spring.onedaythink.chat.service.ChatService;
import com.spring.onedaythink.chat.vo.ChatMessage;
import com.spring.onedaythink.chat.vo.ChatMessageDetail;
import com.spring.onedaythink.chat.vo.ChatRoomDetail;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    @Autowired
    private ChatService chatService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private Logger log = LogManager.getLogger("case3");


    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDetail chatMessageDetail){
        log.debug("연결");
        log.debug(chatMessageDetail);
        List<ChatMessageDetail> msgList = chatService.getChatMessageDetails(ChatRoomDetail.builder().chatRoomNo(chatMessageDetail.getChatRoomNo()).build());
        if (msgList.size() > 0 ) {
//            msgList.forEach(msg -> simpMessagingTemplate.convertAndSend("/sub/chat/room/" + msg.getChatRoomNo(), msg));
        } else {
            chatMessageDetail.setChatMsgContent(chatMessageDetail.getSendNickname() + "님이 채팅방에 참여하였습니다.");
            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatMessageDetail.getChatRoomNo(), chatMessageDetail);
        }
    }

    @MessageMapping("/chat/message")
    public void sendMessage(ChatMessageDetail chatMessageDetail, SimpMessageHeaderAccessor accessor) {
        log.debug("sub test");
        log.debug(chatMessageDetail);
        int result = chatService.addChatMessage(chatMessageDetail);
        log.debug("/sub/chat/room/" + chatMessageDetail.getChatRoomNo());
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatMessageDetail.getChatRoomNo(), chatMessageDetail);
    }

//    @MessageMapping(value = "/chat/enter")
//    public void enter(ChatMessage chatMessage){
//        log.debug("연결");
//        log.debug(chatMessage);
//        chatMessage.setChatMsgContent(chatMessage.getChatSendUserNo() + "님이 채팅방에 참여하였습니다.");
//        log.debug("/sub/chat/room/" + chatMessage.getChatRoomNo());
//        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChatRoomNo(), chatMessage);
//    }

//    @MessageMapping("/chat/message")
//    public void sendMessage(ChatMessage chatMessage, SimpMessageHeaderAccessor accessor) {
//        log.debug("sub test");
//        log.debug(chatMessage);
//        log.debug("/sub/chat/room/" + chatMessage.getChatRoomNo());
//        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getChatRoomNo(), chatMessage);
//    }

//    @MessageMapping("/chat/message")
//    public void sendMessage(@DestinationVariable String chatRoomNo, ChatMessage chatMessage, SimpMessageHeaderAccessor accessor) {
//        log.debug("sub test");
//        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatRoomNo, chatMessage);
//    }
    /*
    @MessageMapping annotation은 메시지의 destination이 /hello였다면 해당 sendMessage() method가 불리도록 해준다.
    sendMessage()에서는 simpMessagingTemplate.convertAndSend를 통해 /sub/chat/{channelId} 채널을 구독 중인 클라이언트에게 메시지를 전송한다.
    SimpMessagingTemplate 는 특정 브로커로 메시지를 전달한다.
    현재는 외부 브로커를 붙이지 않았으므로 인메모리에 정보를 저장한다.
    메시지의 payload는 인자(chatMessage)로 들어온다.
     */
}
