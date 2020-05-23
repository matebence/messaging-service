package com.blesk.messagingservice.Repository.Messages;

import com.blesk.messagingservice.Model.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends MongoRepository<Messages, String> {
}