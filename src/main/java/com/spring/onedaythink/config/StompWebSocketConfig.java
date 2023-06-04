package com.spring.onedaythink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${ip-address}")
    private String IPADRESS;
    //endpoint를 /stomp로 하고, allowedOrigins를 "*"로 하면 페이지에서
    //Get /info 404 Error가 발생한다. 그래서 아래와 같이 2개의 계층으로 분리하고
    //origins를 개발 도메인으로 변경하니 잘 동작하였다.
    //이유는 왜 그런지 아직 찾지 못함
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/ws")
                .setAllowedOrigins("http://"+IPADRESS+":8080", "http://"+IPADRESS+":4000", "http://127.0.0.1:4000", "http://127.0.0.1:8080","http://localhost:8080", "http://localhost:4000")
                .withSockJS();

        registry.addEndpoint("/stomp/notify/ws")
                .setAllowedOrigins("http://"+IPADRESS+":8080", "http://"+IPADRESS+":4000", "http://127.0.0.1:4000", "http://127.0.0.1:8080","http://localhost:8080", "http://localhost:4000")
                .withSockJS();

        registry.addEndpoint("/stomp/haru/ws")
                .setAllowedOrigins("http://"+IPADRESS+":8080", "http://"+IPADRESS+":4000", "http://127.0.0.1:4000", "http://127.0.0.1:8080","http://localhost:8080", "http://localhost:4000")
                .withSockJS();

    }

    /*어플리케이션 내부에서 사용할 path를 지정할 수 있음*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");
    }
}

/*
enableSimpleBroker
해당 경로로 SimpleBroker를 등록. SimpleBroker는 해당하는 경로를 SUBSCRIBE하는 Client에게 메세지를 전달하는 간단한 작업을 수행
enableStompBrokerRelay
SimpleBroker의 기능과 외부 Message Broker( RabbitMQ, ActiveMQ 등 )에 메세지를 전달하는 기능을 가짐
 */
