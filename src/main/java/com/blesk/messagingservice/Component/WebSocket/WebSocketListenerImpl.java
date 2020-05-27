package com.blesk.messagingservice.Component.WebSocket;

import com.blesk.messagingservice.Model.Status;
import com.blesk.messagingservice.Service.Status.StatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketListenerImpl implements WebSocketListener {

    private SimpMessageSendingOperations simpMessageSendingOperations;
    private StatusServiceImpl statusService;

    @Autowired
    public WebSocketListenerImpl(SimpMessageSendingOperations simpMessageSendingOperations, StatusServiceImpl statusService) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.statusService = statusService;
    }

    @Override
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (headerAccessor.getSessionAttributes() == null) return;
        String userName = (String) headerAccessor.getSessionAttributes().get("userName");

        if (userName == null) return;
        Status status = new Status();
        status.setState(Status.State.OFFLINE);
        status.setToken(null);
        status.setUserName(userName);
        Status state = this.statusService.createStatus(status);

        if (state == null) return;
        this.simpMessageSendingOperations.convertAndSend("/status", state);
    }
}