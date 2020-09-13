package com.blesk.messagingservice.Controller;

import com.blesk.messagingservice.Exception.MessageServiceException;
import com.blesk.messagingservice.Model.Conversations;
import com.blesk.messagingservice.Service.Conversations.ConversationsServiceImpl;
import com.blesk.messagingservice.Value.Keys;
import com.blesk.messagingservice.Value.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ConversationsResource {

    private final static int DEFAULT_PAGE_SIZE = 10;
    private final static int DEFAULT_NUMBER = 0;

    private ConversationsServiceImpl conversationsService;

    @Autowired
    public ConversationsResource(ConversationsServiceImpl conversationsService){
        this.conversationsService = conversationsService;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/conversations")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Conversations> createConversations(@Valid @RequestBody Conversations conversations, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Conversations conversation = this.conversationsService.createConversation(conversations);
        if (conversation == null) throw new MessageServiceException(Messages.CREATE_CONVERSATION, HttpStatus.BAD_REQUEST);

        EntityModel<Conversations> entityModel = EntityModel.of(conversation);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveConversations(conversation.getConversationId(), httpServletRequest, httpServletResponse)).withRel("conversation"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @DeleteMapping("/conversations/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteConversations(@PathVariable String conversationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Conversations conversation = this.conversationsService.getConversation(conversationId);
        if (conversation == null) throw new MessageServiceException(Messages.GET_CONVERSATION, HttpStatus.NOT_FOUND);
        if (!this.conversationsService.deleteConversation(conversation)) throw new MessageServiceException(Messages.DELETE_CONVERSATION, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PutMapping("/conversations/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateConversations(@Valid @RequestBody Conversations conversations, @PathVariable String conversationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Conversations conversation = this.conversationsService.getConversation(conversationId);
        if (conversation == null) throw new MessageServiceException(Messages.GET_CONVERSATION, HttpStatus.BAD_REQUEST);

        if (!this.conversationsService.updateConversation(conversation, conversations)) throw new MessageServiceException(Messages.UPDATE_CONVERSATION, HttpStatus.BAD_REQUEST);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/conversations/{conversationId}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Conversations> retrieveConversations(@PathVariable String conversationId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Conversations conversation = this.conversationsService.getConversation(conversationId);
        if (conversation == null) throw new MessageServiceException(Messages.GET_CONVERSATION, HttpStatus.BAD_REQUEST);

        EntityModel<Conversations> entityModel = EntityModel.of(conversation);
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveConversations(conversationId, httpServletRequest, httpServletResponse)).withSelfRel());
        entityModel.add(linkTo(methodOn(this.getClass()).retrieveAllConversations(ConversationsResource.DEFAULT_NUMBER, ConversationsResource.DEFAULT_PAGE_SIZE, httpServletRequest, httpServletResponse)).withRel("all-conversations"));
        return entityModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @GetMapping("/conversations/page/{pageNumber}/limit/{pageSize}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public CollectionModel<Conversations> retrieveAllConversations(@PathVariable int pageNumber, @PathVariable int pageSize, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Conversations> conversations = this.conversationsService.getAllConversations(pageNumber, pageSize);
        if (conversations == null || conversations.isEmpty()) throw new MessageServiceException(Messages.GET_ALL_CONVERSATIONS, HttpStatus.BAD_REQUEST);

        CollectionModel<Conversations> collectionModel = CollectionModel.of(conversations);
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllConversations(pageNumber, pageSize, httpServletRequest, httpServletResponse)).withSelfRel());
        collectionModel.add(linkTo(methodOn(this.getClass()).retrieveAllConversations(++pageNumber, pageSize, httpServletRequest, httpServletResponse)).withRel("next-range"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM') || hasRole('ADMIN') || hasRole('MANAGER') || hasRole('CLIENT') || hasRole('COURIER')")
    @PostMapping("/conversations/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Conversations> searchForConversations(@RequestBody HashMap<String, HashMap<String, String>> search, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (search.get(Keys.PAGINATION) == null) throw new MessageServiceException(Messages.PAGINATION_ERROR, HttpStatus.BAD_REQUEST);
        Map<String, Object> conversation = this.conversationsService.searchForConversation(search);
        if (conversation == null || conversation.isEmpty()) throw new MessageServiceException(Messages.SEARCH_ERROR, HttpStatus.BAD_REQUEST);

        CollectionModel<Conversations> collectionModel = CollectionModel.of((List<Conversations>) conversation.get("results"));
        collectionModel.add(linkTo(methodOn(this.getClass()).searchForConversations(search, httpServletRequest, httpServletResponse)).withSelfRel());

        if ((boolean) conversation.get("hasPrev")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForConversations(search, httpServletRequest, httpServletResponse)).withRel("hasPrev"));
        if ((boolean) conversation.get("hasNext")) collectionModel.add(linkTo(methodOn(this.getClass()).searchForConversations(search, httpServletRequest, httpServletResponse)).withRel("hasNext"));
        return collectionModel;
    }

    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("/conversations/join/{columName}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Conversations> joinConversations(@PathVariable String columName, @RequestBody List<String> ids, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Conversations> conversations = this.conversationsService.getConversationsForJoin(ids, columName);
        if (conversations == null || conversations.isEmpty()) throw new MessageServiceException(Messages.GET_ALL_CONVERSATIONS, HttpStatus.BAD_REQUEST);
        return CollectionModel.of(conversations);
    }
}