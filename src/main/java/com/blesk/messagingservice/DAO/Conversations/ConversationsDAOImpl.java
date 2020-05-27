package com.blesk.messagingservice.DAO.Conversations;

import com.blesk.messagingservice.DAO.DAOImpl;
import com.blesk.messagingservice.Model.Conversations;
import com.blesk.messagingservice.Value.Keys;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ConversationsDAOImpl extends DAOImpl<Conversations> implements ConversationsDAO {

    @Override
    public Conversations save(Conversations conversations) {
        try {
            if (conversations.getConversationId() == null) return super.save(conversations);
            return conversations;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean softDelete(Conversations conversations) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("conversationId").is(conversations.getConversationId()));
            Update update = new Update();
            update.set("isDeleted", true);
            return this.mongoTemplate.updateFirst(query, update, Conversations.class).getModifiedCount() == 1;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Conversations get(String column, String id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(column).is(id).andOperator(Criteria.where("isDeleted").is(false)));
            return this.mongoTemplate.findOne(query, Conversations.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Conversations> getAllConversationsByAccountId(Long accountId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("participants.accountId").is(accountId));
            return this.mongoTemplate.find(query, Conversations.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Conversations> getAll(int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Query query = new Query().with(pageable);
            query.addCriteria(Criteria.where("isDeleted").is(false));
            return new PageImpl<Conversations>(this.mongoTemplate.find(query, Conversations.class), pageable, this.mongoTemplate.count(query, Conversations.class)).getContent();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Map<String, Object> searchBy(HashMap<String, HashMap<String, String>> criterias) {
        HashMap<String, Object> map = new HashMap<>();
        Query query = new Query();
        PageImpl page = null;

        try {
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
                Pageable pageable = PageRequest.of(Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_NUMBER)), Integer.parseInt(criterias.get(Keys.PAGINATION).get(Keys.PAGE_SIZE)));
                long total = this.mongoTemplate.count(query, Conversations.class);
                query.with(pageable);
                query.addCriteria(Criteria.where("isDeleted").is(false));
                page = new PageImpl<Conversations>(this.mongoTemplate.find(query, Conversations.class), pageable, total);

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