package com.blesk.messagingservice.DAO.Conversations;

import com.blesk.messagingservice.DAO.DAO;
import com.blesk.messagingservice.Model.Conversations;

import java.util.List;

public interface ConversationsDAO extends DAO<Conversations> {

    List<Conversations> getAllConversationsByAccountId(Long accountId);
}