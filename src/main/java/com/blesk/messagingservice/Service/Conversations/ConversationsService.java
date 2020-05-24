package com.blesk.messagingservice.Service.Conversations;

import com.blesk.messagingservice.Model.Conversations;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface ConversationsService {

    Conversations createConversation(Conversations conversations);

    Boolean updateConversation(String id, Update update);

    Boolean deleteConversation(Conversations conversations);

    Conversations getConversation(String id);

    List<Conversations> getAllConversations();

    List<Conversations> getAllConversationsByAccontId(Long accountId);
}