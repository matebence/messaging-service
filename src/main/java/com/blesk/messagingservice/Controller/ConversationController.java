package com.blesk.messagingservice.Controller;

import com.blesk.messagingservice.Model.Communications;
import com.blesk.messagingservice.Model.Status;
import com.blesk.messagingservice.Service.Communications.CommunicationsServiceImpl;
import com.blesk.messagingservice.Service.Status.StatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static java.lang.String.format;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class ConversationController {

    private SimpMessageSendingOperations simpMessageSendingOperations;

    private StatusServiceImpl statusService;

    private CommunicationsServiceImpl communicationsService;

    @Autowired
    public ConversationController(SimpMessageSendingOperations simpMessageSendingOperations, StatusServiceImpl statusService, CommunicationsServiceImpl communicationsService){
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.statusService = statusService;
        this.communicationsService = communicationsService;
    }

    @MessageMapping("/state")
    public void setConversationState(@Payload @Valid Status status, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("userName", status.getUserName());
        if(this.statusService.createStatus(status) == null) return;
        this.simpMessageSendingOperations.convertAndSend("/status", status);
    }

    @MessageMapping("/conversations/{conversationId}/sendMessage")
    public void sendCommunicationMessage(@DestinationVariable String conversationId, @Payload @Valid Communications communications) {
        if (this.communicationsService.createCommunication(communications) == null) return;
        this.simpMessageSendingOperations.convertAndSend(format("/conversations/%s", conversationId), communications);
    }
}