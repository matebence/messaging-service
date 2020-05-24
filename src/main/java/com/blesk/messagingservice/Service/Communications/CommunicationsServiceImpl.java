package com.blesk.messagingservice.Service.Communications;

import com.blesk.messagingservice.DAO.Conversations.ConversationsDAOImpl;
import com.blesk.messagingservice.DAO.Communications.CommunicationsDAOImpl;
import com.blesk.messagingservice.Model.Communications;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunicationsServiceImpl implements CommunicationsService {

    private ConversationsDAOImpl conversationsDAO;

    private CommunicationsDAOImpl communicationsDAO;

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
    public Boolean updateCommunication(String id, Update update) {
        return this.communicationsDAO.update(Communications.class, "communicationId", id, update);
    }

    @Override
    @Transactional
    public Boolean deleteCommunication(Communications communications) {
        return this.communicationsDAO.delete(communications);
    }

    @Override
    @Transactional
    public Communications getCommunication(String id) {
        return this.communicationsDAO.get(Communications.class, "communicationId", id);
    }

    @Override
    @Transactional
    public List<Communications> getAllCommunications() {
        return this.communicationsDAO.getAll(Communications.class);
    }
}