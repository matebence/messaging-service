package com.blesk.messagingservice.DAO;

import com.blesk.messagingservice.Model.Conversations;
import com.blesk.messagingservice.Value.Keys;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
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
            update.set("updatedAt", new Timestamp(System.currentTimeMillis()));
            return this.mongoTemplate.updateFirst(query, update, c).getModifiedCount() == 1;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean delete(String column, String id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(column).is(id));
            Update update = new Update();
            update.set("isDeleted", true);
            update.set("deletedAt", new Timestamp(System.currentTimeMillis()));
            return this.mongoTemplate.updateFirst(query, update, Conversations.class).getModifiedCount() == 1;
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
            ArrayList<Criteria> criterias = new ArrayList<Criteria>();
            String initialId = ids.remove(0);
            for (String id : ids){
                criterias.add(Criteria.where(columName).is(id));
            }
            Query query = new Query(Criteria.where(columName).is(initialId).orOperator(criterias.toArray(new Criteria[]{})));
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
                    try{
                        query.addCriteria(Criteria.where(pair.getKey().toString()).is(Integer.parseInt(pair.getValue().toString())));
                    }catch(NumberFormatException ignored){}
                    try{
                        query.addCriteria(Criteria.where(pair.getKey().toString()).is(Float.parseFloat(pair.getValue().toString())));
                    }catch(NumberFormatException ignored){}
                    if(Pattern.compile("^[0-9a-fA-F]{24}$").matcher(pair.getValue().toString()).find()){
                        query.addCriteria(Criteria.where(pair.getKey().toString()).is(Float.parseFloat(pair.getValue().toString())));
                    }else{
                        query.addCriteria(Criteria.where(pair.getKey().toString()).regex(pair.getValue().toString().toLowerCase().replaceAll("\\*", ".*")));
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