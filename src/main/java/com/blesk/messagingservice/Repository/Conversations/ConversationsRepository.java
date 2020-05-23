package com.blesk.messagingservice.Repository.Conversations;

import com.blesk.messagingservice.Model.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationsRepository extends MongoRepository<Messages, String> {
}