package com.blesk.messagingservice.Service.Conversations;

import com.blesk.messagingservice.DAO.Conversations.ConversationsDAOImpl;
import com.blesk.messagingservice.Model.Conversations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConversationsServiceImpl implements ConversationsService {

    private ConversationsDAOImpl conversationsDAO;

    @Autowired
    public ConversationsServiceImpl(ConversationsDAOImpl conversationsDAO) {
        this.conversationsDAO = conversationsDAO;
    }

    @Override
    @Transactional
    public Conversations createConversation(Conversations conversations) {
        return this.conversationsDAO.save(conversations);
    }

    @Override
    @Transactional
    public Boolean updateConversation(Conversations conversation, Conversations conversations) {
        Update update = new Update();
        update.set("isDeleted", conversations.getDeleted());
        update.set("participants", conversations.getParticipants());
        return this.conversationsDAO.update(Conversations.class, "conversationId", conversation.getConversationId(), update);
    }

    @Override
    @Transactional
    public Boolean deleteConversation(Conversations conversations) {
        return this.conversationsDAO.delete("conversationId", conversations.getConversationId());
    }

    @Override
    @Transactional
    public Conversations getConversation(String id) {
        return this.conversationsDAO.get(Conversations.class, "conversationId", id);
    }

    @Override
    @Transactional
    public List<Conversations> getAllConversations(int pageNumber, int pageSize) {
        return this.conversationsDAO.getAll(Conversations.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public List<Conversations> getAllConversationsByAccontId(Long accountId) {
        return this.conversationsDAO.getAllConversationsByAccountId(accountId);
    }

    @Override
    @Transactional
    public List<Conversations> getConversationsForJoin(List<String> ids, String columName) {
        return this.conversationsDAO.getJoinValuesByColumn(Conversations.class, ids, columName);
    }

    @Override
    @Transactional
    public Map<String, Object> searchForConversation(HashMap<String, HashMap<String, String>> criterias) {
        return this.conversationsDAO.searchBy(Conversations.class, criterias);
    }
}