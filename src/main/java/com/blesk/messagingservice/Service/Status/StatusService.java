package com.blesk.messagingservice.Service.Status;

import com.blesk.messagingservice.Model.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StatusService {

    Status createStatus(Status status);

    Boolean updateStatus(Status status, Status statuses);

    Boolean deleteStatus(Status status);

    Status getStatus(String id);

    List<Status> getAllStatuses(int pageNumber, int pageSize);

    Map<String, Object> searchForStatus(HashMap<String, HashMap<String, String>> criteria);
}