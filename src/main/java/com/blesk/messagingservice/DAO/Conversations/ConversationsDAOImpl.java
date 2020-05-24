package com.blesk.messagingservice.DAO.Conversations;

import com.blesk.messagingservice.DAO.DAOImpl;
import com.blesk.messagingservice.Model.Conversations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ConversationsDAOImpl extends DAOImpl<Conversations> implements ConversationsDAO {

    @Override
    public Conversations save(Conversations conversations) {
        try {
            if (conversations.getConversationId() == null) return super.save(conversations);
            return conversations;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Conversations> getAllConversationsByAccountId(Long accountId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("participants.users" + accountId).exists(true));
            return this.mongoTemplate.find(query, Conversations.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}