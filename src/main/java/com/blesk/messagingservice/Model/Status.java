package com.blesk.messagingservice.Model;

import com.blesk.messagingservice.Value.Messages;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
public class Status {

    public enum State{
        ONLINE, OFFLINE
    }

    @Id
    private String statusId;

    @Version
    private Long version;

    @NotNull(message = Messages.STATUS_USER_NAME_NOT_NULL)
    @Size(min = 5, max = 255, message = Messages.STATUS_USER_NAME_SIZE)
    private String userName;

    @NotNull(message = Messages.STATUS_STATE_NOT_NULL)
    private State state;

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

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }
}