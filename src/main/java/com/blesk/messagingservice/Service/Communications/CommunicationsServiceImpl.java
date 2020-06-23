package com.blesk.messagingservice.Service.Communications;

import com.blesk.messagingservice.DAO.Conversations.ConversationsDAOImpl;
import com.blesk.messagingservice.DAO.Communications.CommunicationsDAOImpl;
import com.blesk.messagingservice.Model.Communications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommunicationsServiceImpl implements CommunicationsService {

    private ConversationsDAOImpl conversationsDAO;

    private CommunicationsDAOImpl communicationsDAO;

    @Autowired
    public CommunicationsServiceImpl(ConversationsDAOImpl conversationsDAO, CommunicationsDAOImpl communicationsDAO){
        this.conversationsDAO = conversationsDAO;
        this.communicationsDAO = communicationsDAO;
    }

    @Override
    @Transactional
    public Communications createCommunication(Communications communications) {
        this.conversationsDAO.save(communications.getConversations());
        return this.communicationsDAO.save(communications);
    }

    @Override
    @Transactional
    public Boolean updateCommunication(Communications communication, Communications communications) {
        Update update = new Update();
        update.set("userName", communications.getUserName());
        update.set("sender", communications.getSender());
        update.set("content", communications.getContent());
        update.set("conversations", communications.getConversations());
        update.set("date", communications.getDate());
        return this.communicationsDAO.update(Communications.class, "communicationId", communication.getCommunicationId(), update);
    }

    @Override
    @Transactional
    public Boolean deleteCommunication(Communications communications) {
        return this.communicationsDAO.delete(Communications.class,"communicationId", communications.getCommunicationId());
    }

    @Override
    @Transactional
    public Communications getCommunication(String id) {
        return this.communicationsDAO.get(Communications.class, "communicationId", id);
    }

    @Override
    @Transactional
    public List<Communications> getAllCommunications(int pageNumber, int pageSize) {
        return this.communicationsDAO.getAll(Communications.class, pageNumber, pageSize);
    }

    @Override
    @Transactional
    public List<Communications> getCommunicationsForJoin(List<String> ids, String columName) {
        return this.communicationsDAO.getJoinValuesByColumn(Communications.class, ids, columName);
    }

    @Override
    @Transactional
    public Map<String, Object> searchForCommunication(HashMap<String, HashMap<String, String>> criterias) {
        return this.conversationsDAO.searchBy(Communications.class, criterias);
    }
}