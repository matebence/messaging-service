package com.blesk.messagingservice.DAO.Messages;

import com.blesk.messagingservice.DAO.DAOImpl;
import com.blesk.messagingservice.Model.Communications;
import org.springframework.stereotype.Repository;

@Repository
public class MessagesDAOImpl extends DAOImpl<Communications> implements MessagesDAO {
}