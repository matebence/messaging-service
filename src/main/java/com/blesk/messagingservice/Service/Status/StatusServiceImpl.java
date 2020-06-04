package com.blesk.messagingservice.Service.Status;

import com.blesk.messagingservice.DAO.Status.StatusDAOImpl;
import com.blesk.messagingservice.Model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatusServiceImpl implements StatusService {

    private StatusDAOImpl statusDAO;

    @Autowired
    public StatusServiceImpl(StatusDAOImpl statusDAO){
        this.statusDAO = statusDAO;
    }

    @Override
    @Transactional
    public Status createStatus(Status status) {
        return this.statusDAO.save(status);
    }

    @Override
    @Transactional
    public Boolean updateStatus(Status status, Status statuses) {
        Update update = new Update();
        update.set("userName", statuses.getUserName());
        update.set("token", statuses.getToken());
        update.set("state", statuses.getState());
        return this.statusDAO.update(Status.class, "statusId", status.getStatusId(), update);
    }

    @Override
    @Transactional
    public Boolean deleteStatus(Status status) {
        return this.statusDAO.delete("statusId", status.getStatusId());
    }

    @Override
    @Transactional
    public Status getStatus(String id) {
        return this.statusDAO.get(Status.class, "statusId", id);
    }

    @Override
    @Transactional
    public List<Status> getAllStatuses(int pageNumber, int pageSize) {
        return this.statusDAO.getAll(Status.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public Map<String, Object> searchForStatus(HashMap<String, HashMap<String, String>> criterias) {
        return this.statusDAO.searchBy(Status.class, criterias);
    }
}