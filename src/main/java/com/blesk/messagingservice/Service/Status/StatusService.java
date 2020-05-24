package com.blesk.messagingservice.Service.Status;

import com.blesk.messagingservice.Model.Status;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface StatusService {

    Status createStatus(Status status);

    Boolean updateStatus(String id, Update update);

    Boolean deleteStatus(Status status);

    Status getStatus(String id);

    List<Status> getAllStatuses();
}