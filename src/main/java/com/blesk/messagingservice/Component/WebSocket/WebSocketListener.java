package com.blesk.messagingservice.Component.WebSocket;

import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public interface WebSocketListener {

    void handleWebSocketDisconnectListener(SessionDisconnectEvent event);
}