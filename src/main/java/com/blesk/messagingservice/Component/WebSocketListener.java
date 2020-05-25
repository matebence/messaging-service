package com.blesk.messagingservice.Component;

import com.blesk.messagingservice.Model.Status;
import com.blesk.messagingservice.Service.Status.StatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketListener {

    private SimpMessageSendingOperations simpMessageSendingOperations;
    private StatusServiceImpl statusService;

    @Autowired
    public WebSocketListener(SimpMessageSendingOperations simpMessageSendingOperations, StatusServiceImpl statusService) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.statusService = statusService;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userName = (String) headerAccessor.getSessionAttributes().get("userName");

        if (userName == null) return;
        Status status = new Status();
        status.setState(Status.State.OFFLINE);
        status.setUserName(userName);

        if (this.statusService.createStatus(status) == null) return;
        this.simpMessageSendingOperations.convertAndSend("/status", status);
    }
}