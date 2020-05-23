package com.blesk.messagingservice.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Set;

@Document
public class Conversations {

    @Id
    private String conversationId;

    private Set<HashMap<Long, String>> participants;

    public Conversations() {
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Set<HashMap<Long, String>> getParticipants() {
        return this.participants;
    }

    public void setParticipants(Set<HashMap<Long, String>> participants) {
        this.participants = participants;
    }
}