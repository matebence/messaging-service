package com.blesk.messagingservice.Service.Communications;

import com.blesk.messagingservice.Model.Communications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommunicationsService {

    Communications createCommunication(Communications communications);

    Boolean updateCommunication(Communications communication, Communications communications);

    Boolean deleteCommunication(Communications communications);

    Communications getCommunication(String id);

    List<Communications> getAllCommunications(int pageNumber, int pageSize);

    Map<String, Object> searchForCommunication(HashMap<String, HashMap<String, String>> criterias);
}