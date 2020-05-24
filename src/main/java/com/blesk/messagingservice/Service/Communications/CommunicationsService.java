package com.blesk.messagingservice.Service.Communications;

import com.blesk.messagingservice.Model.Communications;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public interface CommunicationsService {

    Communications createCommunication(Communications communications);

    Boolean updateCommunication(String id, Update update);

    Boolean deleteCommunication(Communications communications);

    Communications getCommunication(String id);

    List<Communications> getAllCommunications();
}