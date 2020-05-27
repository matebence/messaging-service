package com.blesk.messagingservice.DAO.Status;

import com.blesk.messagingservice.DAO.DAOImpl;
import com.blesk.messagingservice.Model.Status;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class StatusDAOImpl extends DAOImpl<Status> implements StatusDAO {

    @Override
    public Status save(Status status) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("userName").is(status.getUserName()));
            Update update = new Update();
            update.set("state", status.getState());
            update.set("token", status.getToken());
            this.mongoTemplate.upsert(query, update, Status.class);
            return this.mongoTemplate.findOne(query, Status.class);
        } catch (Exception e) {
            return null;
        }
    }
}