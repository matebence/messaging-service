package com.blesk.messagingservice.DAO;

import com.blesk.messagingservice.Value.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Pattern;

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
            update.set("updatedAt", new Date());
            return this.mongoTemplate.updateFirst(query, update, c).getModifiedCount() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean delete(Class<T> c, String column, String id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(column).is(id));
            Update update = new Update();
            update.set("isDeleted", true);
            update.set("deletedAt", new Date());
            return this.mongoTemplate.updateFirst(query, update, c).getModifiedCount() == 1;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public T get(Class<T> c, String column, String id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(column).is(id).andOperator(Criteria.where("isDeleted").is(false)));
            return this.mongoTemplate.findOne(query, c);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<T> getAll(Class<T> c, int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Query query = new Query().with(pageable);
            query.addCriteria(Criteria.where("isDeleted").is(false));
            return new PageImpl<T>(this.mongoTemplate.find(query, c), pageable, this.mongoTemplate.count(query, c)).getContent();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<T> getJoinValuesByColumn(Class<T> c, List<String> ids, String columName) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(columName).in(ids).andOperator(Criteria.where("isDeleted").is(false)));
            return this.mongoTemplate.find(query, c);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, Object> searchBy(Class c, HashMap<String, HashMap<String, String>> criterias) {
        HashMap<String, Object> map = new HashMap<>();
        Query query = new Query();
        PageImpl page = null;

        try {
            if (criterias.get(Keys.SEARCH) != null) {
                for (Object o : criterias.get(Keys.SEARCH).entrySet()) {
                    Map.Entry pair = (Map.Entry) o;
                    if (Pattern.compile("^[0-9a-fA-F]{24}$").matcher(pair.getValue().toString()).find()){
                        query.addCriteria(Criteria.where(pair.getKey().toString()).is(pair.getValue().toString()));
                    } else if(Pattern.compile("^[\\D][a-zA-Z0-9 ]*$").matcher(pair.getValue().toString()).find()) {
                        query.addCriteria(Criteria.where(pair.getKey().toString()).regex(pair.getValue().toString().toLowerCase().replaceAll("\\*", ".*")));
                    } else {
                        query.addCriteria(Criteria.where(pair.getKey().toString()).is(Float.parseFloat(pair.getValue().toString())));
                    }
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
                Pageable pageable = PageRequest.of(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                long total = this.mongoTemplate.count(query, c);
                query.with(pageable);
                query.addCriteria(Criteria.where("isDeleted").is(false));
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