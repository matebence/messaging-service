package com.blesk.messagingservice.Service.Conversations;

import com.blesk.messagingservice.DAO.Conversations.ConversationsDAOImpl;
import com.blesk.messagingservice.Model.Conversations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConversationsServiceImpl implements ConversationsService {

    private ConversationsDAOImpl conversationsDAO;

    @Autowired
    public ConversationsServiceImpl(ConversationsDAOImpl conversationsDAO){
        this.conversationsDAO = conversationsDAO;
    }

    @Override
    @Transactional
    public Conversations createConversation(Conversations conversations) {
        return this.conversationsDAO.save(conversations);
    }

    @Override
    @Transactional
    public Boolean updateConversation(String id, Update update) {
        return this.conversationsDAO.update(Conversations.class, "conversationId", id, update);
    }

    @Override
    @Transactional
    public Boolean deleteConversation(Conversations conversations) {
        return this.conversationsDAO.delete(conversations);
    }

    @Override
    @Transactional
    public Conversations getConversation(String id) {
        return this.conversationsDAO.get(Conversations.class, "conversationId", id);
    }

    @Override
    @Transactional
    public List<Conversations> getAllConversations() {
        return this.conversationsDAO.getAll(Conversations.class);
    }

    @Override
    @Transactional
    public List<Conversations> getAllConversationsByAccontId(Long accountId) {
        return this.conversationsDAO.getAllConversationsByAccountId(accountId);
    }
}