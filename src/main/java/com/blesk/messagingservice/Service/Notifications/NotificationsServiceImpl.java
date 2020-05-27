package com.blesk.messagingservice.Service.Notifications;

import com.blesk.messagingservice.Component.PushNotification.PushNotificationImpl;
import com.blesk.messagingservice.DTO.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private PushNotificationImpl pushNotificationImpl;

    @Autowired
    public NotificationsServiceImpl(PushNotificationImpl pushNotificationImpl){
        this.pushNotificationImpl = pushNotificationImpl;
    }

    @Override
    public Boolean sendPushNotificationToToken(Notifications notifications) {
        try {
            return this.pushNotificationImpl.sendNotification(notifications);
        } catch (InterruptedException | ExecutionException e) {
            return Boolean.FALSE;
        }
    }
}