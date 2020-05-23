package com.blesk.messagingservice.DAO.Conversations;

import com.blesk.messagingservice.DAO.DAOImpl;
import com.blesk.messagingservice.Model.Conversations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConversationsDAOImpl extends DAOImpl<Conversations> implements ConversationsDAO {

    @Override
    public List<Conversations> getAllConversationsByAccountId(Long accountId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("participants." + accountId).exists(true));
        return this.mongoTemplate.find(query, Conversations.class);
    }
}