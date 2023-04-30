package com.spring.onedaythink.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
//                .setAllowedOrigins("http://localhost:4000", "http://localhost:8080")
                .setAllowedOriginPatterns("http://localhost:4000", "http://localhost:8080")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

}

/*
    앞으로 웹소켓 서버의 엔드포인트는 /ws이다.
    클라이언트는 다른 origin이므로 cors 오류를 방지하기 위해 setAllowedOrigins를 미리 사용해서 허용할 origin을 등록해둔다.
    * 는 모든 origin 허용이다.
    Message broker를 설정하기 위해 configureMessageBroker method를 overide 한다.

    enableSimpleBroker()를 사용해서 /sub가 prefix로 붙은 destination의 클라이언트에게 메시지를 보낼 수 있도록 Simple Broker를 등록한다.
    setApplicationestinationPrefiexs()를 사용해서 /pub가 prefix로 붙은 메시지들은 @MessageMapping이 붙은 method로 바운드된다.
*/