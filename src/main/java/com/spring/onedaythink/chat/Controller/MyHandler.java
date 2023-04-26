package com.spring.onedaythink.chat.Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class MyHandler implements WebSocketHandler {

    private Logger log = LogManager.getLogger("case3");

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 연결이 성공했을 때 호출되는 메소드
        log.debug("socket connection");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        // 메시지를 받았을 때 호출되는 메소드
        log.debug("socket receive msg");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        // 에러가 발생했을 때 호출되는 메소드
        log.debug("socket error");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        // 연결이 종료되었을 때 호출되는 메소드
        log.debug("socket disconnection");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}