package com.blesk.messagingservice.Repository.Status;

import com.blesk.messagingservice.Model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends MongoRepository<Status, String> {
}
