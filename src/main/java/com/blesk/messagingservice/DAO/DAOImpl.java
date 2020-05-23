package com.blesk.messagingservice.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DAOImpl<T> implements DAO<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public T save(T t) {
        return this.mongoTemplate.save(t);
    }

    @Override
    public Boolean update(Class<T> c, String column, String id, Update update) {
        Query query = new Query();
        query.addCriteria(Criteria.where(column).is(id));
        return this.mongoTemplate.updateFirst(query, update, c).getModifiedCount() == 1;
    }

    @Override
    public Boolean delete(T t) {
        return mongoTemplate.remove(t).getDeletedCount() == 1;
    }

    @Override
    public T get(Class<T> c, String column, String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where(column).is(id));
        return mongoTemplate.findOne(query, c);
    }

    @Override
    public List<T> getAll(Class<T> c) {
        return mongoTemplate.findAll(c);
    }
}