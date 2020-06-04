package com.blesk.messagingservice.Model;

import java.sql.Timestamp;
import java.util.Date;

import com.blesk.messagingservice.Value.Messages;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Document(collection = "communications")
public class Communications {

    @Id
    private String communicationId;

    @Version
    private Long version;

    @Indexed(unique = true)
    @NotNull(message = Messages.COMMUNICATION_USER_NAME_NOT_NULL)
    @Size(min = 5, max = 255, message = Messages.COMMUNICATION_USER_NAME_SIZE)
    private String userName;

    @NotNull(message = Messages.COMMUNICATION_SENDER_NOT_NULL)
    @Positive(message = Messages.COMMUNICATION_SENDER_POSITIVE)
    private Long sender;

    @NotNull(message = Messages.COMMUNICATION_CONTENT_NOT_NULL)
    @Size(min = 3, max = 255, message = Messages.COMMUNICATION_CONTENT_SIZE)
    private String content;

    @Reference
    @NotNull(message = Messages.COMMUNICATION_CONVERSATIONS_NOT_NULL)
    private Conversations conversations;

    private Date date = new Date();

    private Boolean isDeleted = false;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    private Timestamp updatedAt = null;

    private Timestamp deletedAt = null;

    public Communications() {
    }

    public String getCommunicationId() {
        return this.communicationId;
    }

    public void setCommunicationId(String communicationId) {
        this.communicationId = communicationId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Conversations getConversations() {
        return this.conversations;
    }

    public void setConversations(Conversations conversations) {
        this.conversations = conversations;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}