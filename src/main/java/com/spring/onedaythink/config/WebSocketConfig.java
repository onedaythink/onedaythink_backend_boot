package com.spring.onedaythink.config;

import com.spring.onedaythink.chat.Controller.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(myHandler(), "/myHandler")
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }
}

/*
    위 코드에서는 WebSocketHandler 인터페이스의 메소드를 구현하여
    WebSocket 연결 시 동작하는 메소드들을 작성합니다.
    afterConnectionEstablished 메소드는 WebSocket 연결이 성공했을 때 호출되며,
    handleMessage 메소드는 WebSocket으로부터 메시지를 받았을 때 호출됩니다.
    handleTransportError 메소드는 에러가 발생했을 때 호출되며,
    afterConnectionClosed 메소드는 WebSocket 연결이 종료되었을 때 호출됩니다.

    이제 백엔드와 프론트엔드에서 WebSocket을 사용할 수 있습니다.
    웹소켓을 이용하면 실시간으로 데이터를 주고받을 수 있으므로, 채팅이나 알림 등에 사용될 수 있습니다.
 */