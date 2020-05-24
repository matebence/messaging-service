package com.blesk.messagingservice.Repository.Communications;

import com.blesk.messagingservice.Model.Communications;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationsRepository extends MongoRepository<Communications, String> {
}