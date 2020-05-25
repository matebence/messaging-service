package com.blesk.messagingservice.Service.Status;

import com.blesk.messagingservice.DAO.Status.StatusDAOImpl;
import com.blesk.messagingservice.Model.Status;
import com.blesk.messagingservice.Utilitie.Tools;
import com.blesk.messagingservice.Value.Keys;
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
        update.set("userName", Tools.getNotNull(statuses.getUserName(), status.getUserName()));
        update.set("state", Tools.getNotNull(statuses.getState(), status.getState()));
        return this.statusDAO.update(Status.class, "statusId", status.getStatusId(), update);
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
    public List<Status> getAllStatuses(int pageNumber, int pageSize) {
        return this.statusDAO.getAll(pageNumber, pageSize, Status.class);
    }

    @Override
    @Transactional
    public Map<String, Object> searchForStatus(HashMap<String, HashMap<String, String>> criteria) {
        return this.statusDAO.searchBy(Status.class, criteria, Integer.parseInt(criteria.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)));
    }
}