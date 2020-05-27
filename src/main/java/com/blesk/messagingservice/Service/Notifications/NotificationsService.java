package com.blesk.messagingservice.Service.Notifications;

import com.blesk.messagingservice.DTO.Notifications;

public interface NotificationsService {

    Boolean sendPushNotificationToToken(Notifications notifications);
}