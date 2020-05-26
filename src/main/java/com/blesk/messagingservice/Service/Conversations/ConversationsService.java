package com.blesk.messagingservice.Service.Conversations;

import com.blesk.messagingservice.Model.Conversations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConversationsService {

    Conversations createConversation(Conversations conversations);

    Boolean updateConversation(Conversations conversation, Conversations conversations);

    Boolean deleteConversation(Conversations conversations, boolean su);

    Conversations getConversation(String id, boolean su);

    List<Conversations> getAllConversations(int pageNumber, int pageSize, boolean su);

    List<Conversations> getAllConversationsByAccontId(Long accountId);

    Map<String, Object> searchForConversation(HashMap<String, HashMap<String, String>> criterias, boolean su);
}