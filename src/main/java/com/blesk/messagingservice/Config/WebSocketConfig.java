package com.blesk.messagingservice.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer implements WebSocketMessageBrokerConfigurer {

    @Value("${blesk.cors.allowed.origin}")
    private String origin;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        if(stompEndpointRegistry != null) stompEndpointRegistry.addEndpoint("/ws").setAllowedOrigins("http://localhost:4300").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        if(messageBrokerRegistry != null){
            messageBrokerRegistry.setApplicationDestinationPrefixes("/blesk");
            messageBrokerRegistry.enableSimpleBroker("/conversations", "/status");
        }
    }

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}