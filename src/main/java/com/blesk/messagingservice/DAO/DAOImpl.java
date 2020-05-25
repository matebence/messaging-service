package com.blesk.messagingservice.DAO;

import com.blesk.messagingservice.Value.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<T> getAll(int pageNumber, int pageSize, Class<T> c) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Query query = new Query().with(pageable);
            return new PageImpl<T>(this.mongoTemplate.find(query, c), pageable, this.mongoTemplate.count(query, c)).getContent();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, Object> searchBy(Class c, HashMap<String, HashMap<String, String>> criterias, int pageNumber) {
        final int PAGE_SIZE = 10;
        try {
            HashMap<String, Object> map = new HashMap<>();
            Query query = new Query();
            PageImpl page = null;

            if (criterias.get(Keys.SEARCH) != null) {
                for (Object o : criterias.get(Keys.SEARCH).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    query.addCriteria(Criteria.where(pair.getKey().toString()).regex(pair.getValue().toString().toLowerCase().replaceAll("\\*", ".*")));
                }
            }
            if (criterias.get(Keys.ORDER_BY) != null) {
                for (Object o : criterias.get(Keys.ORDER_BY).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    if (pair.getValue().toString().toLowerCase().equals("asc")) {
                        query.with(Sort.by(Sort.Direction.ASC, pair.getKey().toString()));
                    } else if (pair.getValue().toString().toLowerCase().equals("desc")) {
                        query.with(Sort.by(Sort.Direction.DESC, pair.getKey().toString()));
                    }
                }
            }
            if (criterias.get(Keys.PAGINATION) != null) {
                Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
                long total = this.mongoTemplate.count(query, c);
                query.with(pageable);
                page = new PageImpl<T>(this.mongoTemplate.find(query, c), pageable, total);

                map.put("hasPrev", page.getNumber() > 0);
                map.put("hasNext", page.getNumber() < total - 1);
            }

            if (page == null) return null;
            map.put("results", page.getContent());
            return map;
        } catch (Exception e) {
            return Collections.<String, Object>emptyMap();
        }
    }
}