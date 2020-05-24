package com.blesk.messagingservice.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class DAOImpl<T> implements DAO<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public T save(T t) {
        try {
            this.mongoTemplate.save(t);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean update(Class<T> c, String column, String id, Update update) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(column).is(id));
            return this.mongoTemplate.updateFirst(query, update, c).getModifiedCount() == 1;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean delete(T t) {
        try {
            return mongoTemplate.remove(t).getDeletedCount() == 1;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public T get(Class<T> c, String column, String id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(column).is(id));
            return mongoTemplate.findOne(query, c);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<T> getAll(Class<T> c) {
        try {
            return mongoTemplate.findAll(c);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}