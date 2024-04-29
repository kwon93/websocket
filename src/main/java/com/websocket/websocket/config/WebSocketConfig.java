package com.websocket.websocket.config;

import com.websocket.websocket.handler.SocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler(), "/socketHandler").setAllowedOriginPatterns("*");

    }

    @Bean
    public WebSocketHandler socketHandler(){
        return new SocketHandler();
    }
}
