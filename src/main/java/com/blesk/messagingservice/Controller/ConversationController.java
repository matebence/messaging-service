package com.blesk.messagingservice.Controller;

import com.blesk.messagingservice.DTO.Notifications;
import com.blesk.messagingservice.Model.Communications;
import com.blesk.messagingservice.Model.Conversations;
import com.blesk.messagingservice.Model.Status;
import com.blesk.messagingservice.Service.Communications.CommunicationsServiceImpl;
import com.blesk.messagingservice.Service.Notifications.NotificationsServiceImpl;
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

import java.util.HashMap;

import static java.lang.String.format;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class ConversationController {

    private SimpMessageSendingOperations simpMessageSendingOperations;

    private StatusServiceImpl statusService;

    private CommunicationsServiceImpl communicationsService;

    private NotificationsServiceImpl notificationsService;

    @Autowired
    public ConversationController(SimpMessageSendingOperations simpMessageSendingOperations, StatusServiceImpl statusService, CommunicationsServiceImpl communicationsService, NotificationsServiceImpl notificationsService) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.statusService = statusService;
        this.communicationsService = communicationsService;
        this.notificationsService = notificationsService;
    }

    @MessageMapping("/state")
    public void setConversationState(@Payload @Valid Status status, SimpMessageHeaderAccessor headerAccessor) {
        if (headerAccessor.getSessionAttributes() == null) return;
        headerAccessor.getSessionAttributes().put("userName", status.getUserName());
        Status state = this.statusService.createStatus(status);
        if (state == null) return;
        this.simpMessageSendingOperations.convertAndSend("/status", state);
    }

    @MessageMapping("/conversations/{conversationId}/sendMessage")
    public void sendCommunicationMessage(@DestinationVariable String conversationId, @Payload @Valid Communications communications) {
        Communications communication = this.communicationsService.createCommunication(communications);
        if (communication == null) return;
        for (Conversations.Users users : communications.getConversations().getParticipants()) {
            if (!communications.getSender().equals(users.getAccountId())) {
                Status status = this.statusService.getStatus(users.getStatus().getStatusId());

                Notifications notifications = new Notifications();
                notifications.setBody(communications.getContent());
                notifications.setToken(status.getToken());
                notifications.setData(new HashMap<String, String>(){{put("lastConversionId", communication.getCommunicationId());}});

                if (communications.getContent().length() > 5) notifications.setBody(communications.getContent().substring(0, 5).concat("..."));
                notifications.setTitle(communications.getConversations().getParticipants().stream().filter(user -> !communications.getSender().equals(user.getAccountId())).map(userName -> userName.getUserName().concat(" ")).reduce("", String::concat));

                this.notificationsService.sendPushNotificationToToken(notifications);
            }
        }
        this.simpMessageSendingOperations.convertAndSend(format("/conversations/%s", conversationId), communications);
    }
}