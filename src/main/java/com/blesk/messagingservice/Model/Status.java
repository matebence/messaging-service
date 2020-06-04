package com.blesk.messagingservice.Model;

import com.blesk.messagingservice.Validator.Contains;
import com.blesk.messagingservice.Value.Messages;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Document(collection = "status")
public class Status {

    public enum State {
        ONLINE, OFFLINE
    }

    @Id
    private String statusId;

    @Version
    private Long version;

    @Indexed(unique = true)
    @NotNull(message = Messages.STATUS_USER_NAME_NOT_NULL)
    @Size(min = 5, max = 255, message = Messages.STATUS_USER_NAME_SIZE)
    private String userName;

    @Indexed(unique = true)
    @NotNull(message = Messages.STATUS_TOKEN_NOT_NULL)
    private String token;

    @Contains(enumClass = State.class, message = Messages.STATUS_STATE_CONTAINS)
    @NotNull(message = Messages.STATUS_STATE_NOT_NULL)
    private String state;

    private Boolean isDeleted = false;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    private Timestamp updatedAt = null;

    private Timestamp deletedAt = null;

    public Status() {
    }

    public String getStatusId() {
        return this.statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
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