package com.spring.onedaythink.chat.Controller;


import com.spring.onedaythink.chat.vo.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private Logger log = LogManager.getLogger("case3");

    @MessageMapping("/chat/{chatRoomNo}")
    public void sendMessage(@DestinationVariable String chatRoomNo, ChatMessage chatMessage, SimpMessageHeaderAccessor accessor) {
        log.debug("연결");
        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatRoomNo, chatMessage);
    }

    /*
    @MessageMapping annotation은 메시지의 destination이 /hello였다면 해당 sendMessage() method가 불리도록 해준다.
    sendMessage()에서는 simpMessagingTemplate.convertAndSend를 통해 /sub/chat/{channelId} 채널을 구독 중인 클라이언트에게 메시지를 전송한다.
    SimpMessagingTemplate 는 특정 브로커로 메시지를 전달한다.
    현재는 외부 브로커를 붙이지 않았으므로 인메모리에 정보를 저장한다.
    메시지의 payload는 인자(chatMessage)로 들어온다.
     */
}
