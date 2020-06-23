package com.blesk.messagingservice.Service.Conversations;

import com.blesk.messagingservice.Model.Conversations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConversationsService {

    Conversations createConversation(Conversations conversations);

    Boolean updateConversation(Conversations conversation, Conversations conversations);

    Boolean deleteConversation(Conversations conversations);

    Conversations getConversation(String id);

    List<Conversations> getAllConversations(int pageNumber, int pageSize);

    List<Conversations> getAllConversationsByAccontId(Long accountId);

    List<Conversations> getConversationsForJoin(List<String> ids, String columName);

    Map<String, Object> searchForConversation(HashMap<String, HashMap<String, String>> criterias);
}