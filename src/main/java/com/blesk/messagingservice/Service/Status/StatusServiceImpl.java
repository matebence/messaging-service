package com.blesk.messagingservice.Service.Status;

import com.blesk.messagingservice.DAO.Status.StatusDAOImpl;
import com.blesk.messagingservice.Model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Boolean updateStatus(String id, Update update) {
        return this.statusDAO.update(Status.class, "statusId", id, update);
    }

    @Override
    @Transactional
    public Boolean deleteStatus(Status status) {
        return this.statusDAO.delete(status);
    }

    @Override
    @Transactional
    public Status getStatus(String id) {
        return this.statusDAO.get(Status.class, "statusId", id);
    }

    @Override
    @Transactional
    public List<Status> getAllStatuses() {
        return this.statusDAO.getAll(Status.class);
    }
}