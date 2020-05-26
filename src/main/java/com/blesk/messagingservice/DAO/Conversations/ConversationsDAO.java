package com.blesk.messagingservice.DAO.Conversations;

import com.blesk.messagingservice.DAO.DAO;
import com.blesk.messagingservice.Model.Conversations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConversationsDAO extends DAO<Conversations> {

    Boolean softDelete(Conversations conversations);

    Conversations get(String column, String id);

    List<Conversations> getAll(int pageNumber, int pageSize);

    List<Conversations> getAllConversationsByAccountId(Long accountId);

    Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias);
}