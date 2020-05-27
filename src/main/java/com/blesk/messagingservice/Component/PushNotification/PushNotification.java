package com.blesk.messagingservice.Component.PushNotification;

import com.blesk.messagingservice.DTO.Notifications;
import com.google.firebase.messaging.*;

import java.util.concurrent.ExecutionException;

public interface PushNotification {

    public Boolean sendNotification(Notifications notifications) throws InterruptedException, ExecutionException;

    String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException;

    WebpushConfig getWebpushConfig();

    Message getPreconfiguredMessageToToken(Notifications notifications);

    Message.Builder getPreconfiguredMessageBuilder(Notifications notifications);
}