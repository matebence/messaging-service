package com.blesk.messagingservice.Repository.Conversations;

import com.blesk.messagingservice.Model.Conversations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationsRepository extends MongoRepository<Conversations, String> {
}