package com.blesk.messagingservice.Config;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.sockjs.client.InfoReceiver;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@Configuration
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer implements WebSocketMessageBrokerConfigurer, InfoReceiver {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        if(stompEndpointRegistry != null) stompEndpointRegistry.addEndpoint("/ws").setAllowedOrigins("http://192.168.99.100:8765").withSockJS();
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

    @Override
    public String executeInfoRequest(URI infoUrl, HttpHeaders headers) {
        HttpGet httpGet = new HttpGet(infoUrl);
        httpGet.setHeader("Access-Control-Allow-Origin", "http://localhost:4300");
        HttpClient httpClient = HttpClients.createDefault();

        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            List<String> output = IOUtils.readLines(httpResponse.getEntity().getContent());

            return output.get(0);
        } catch (IOException ex) {
            return null;
        }
    }
}