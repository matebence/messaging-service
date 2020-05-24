package com.blesk.messagingservice.Model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Document
public class Messages {

    @Id
    private String messageId;

    @Version
    private Long version;

    @NotNull(message = "")
    @Size(min = 3, max = 32, message = "")
    private String firstName;

    @NotNull(message = "")
    @Size(min = 3, max = 32, message = "")
    private String lastName;

    @NotNull(message = "")
    @Positive(message = "")
    private Long sender;

    @NotNull(message = "")
    @Size(min = 3, max = 255, message = "")
    private String content;

    @NotNull(message = "")
    @FutureOrPresent(message = "")
    private Date date = new Date();

    @NotNull(message = "")
    private MessageType messageType;

    @NotNull(message = "")
    private Conversations conversations;

    public Messages() {
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getSender() {
        return this.sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Conversations getConversations() {
        return this.conversations;
    }

    public void setConversations(Conversations conversations) {
        this.conversations = conversations;
    }
}