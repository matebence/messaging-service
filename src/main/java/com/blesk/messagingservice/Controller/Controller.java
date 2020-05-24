package com.blesk.messagingservice.Controller;

import com.blesk.messagingservice.Model.Communications;
import com.blesk.messagingservice.Model.Conversations;
import com.blesk.messagingservice.Service.Conversations.ConversationsServiceImpl;
import com.blesk.messagingservice.Service.Communications.CommunicationsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/")
public class Controller {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final CommunicationsServiceImpl messagesService;
    private final ConversationsServiceImpl conversationsService;

    public Controller(CommunicationsServiceImpl messagesService, ConversationsServiceImpl conversationsService) {
        this.messagesService = messagesService;
        this.conversationsService = conversationsService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Communications> getAllMessages() {
        LOG.info("Getting all messages.");
        return messagesService.getAllCommunications();
    }

    @RequestMapping(value = "/{messageId}", method = RequestMethod.GET)
    public Communications getMessage(@PathVariable String messageId) {
        LOG.info("Getting message with ID: {}.", messageId);
        return messagesService.getCommunication(messageId);
    }

    @RequestMapping(value = "test/{accountId}", method = RequestMethod.GET)
    public List<Conversations> getMessage(@PathVariable Long accountId) {
        LOG.info("Getting message with ID: {}.", accountId);
        return conversationsService.getAllConversationsByAccontId(accountId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Communications addNewMessages(@RequestBody @Valid Communications communications) {
        LOG.info("Saving message.");
        return this.messagesService.createCommunication(communications);
    }
}