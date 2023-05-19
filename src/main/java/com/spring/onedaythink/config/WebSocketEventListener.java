package com.spring.onedaythink.config;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener implements ApplicationListener<SessionConnectedEvent> {

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        // 웹소켓 연결 시 호출되는 메서드
        System.out.println("WebSocket Connected: " + event.getUser().getName());
    }

    public void onApplicationEvent(SessionDisconnectEvent event) {
        // 웹소켓 연결 종료 시 호출되는 메서드
        System.out.println("WebSocket Disconnected: " + event.getUser().getName());
    }
}