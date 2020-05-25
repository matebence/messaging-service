package com.blesk.messagingservice.Service.Conversations;

import com.blesk.messagingservice.DAO.Conversations.ConversationsDAOImpl;
import com.blesk.messagingservice.Model.Conversations;
import com.blesk.messagingservice.Utilitie.Tools;
import com.blesk.messagingservice.Value.Keys;
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
    public Boolean updateConversation(Conversations conversation, Conversations conversations) {
        Update update = new Update();
        update.set("participants", Tools.getNotNull(conversations.getParticipants(), conversation.getParticipants()));
        return this.conversationsDAO.update(Conversations.class, "conversationId", conversation.getConversationId(), update);
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
    public List<Conversations> getAllConversations(int pageNumber, int pageSize) {
        return this.conversationsDAO.getAll(pageNumber, pageSize, Conversations.class);
    }

    @Override
    @Transactional
    public List<Conversations> getAllConversationsByAccontId(Long accountId) {
        return this.conversationsDAO.getAllConversationsByAccountId(accountId);
    }

    @Override
    @Transactional
    public Map<String, Object> searchForConversation(HashMap<String, HashMap<String, String>> criteria) {
        return this.conversationsDAO.searchBy(Conversations.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
    }
}