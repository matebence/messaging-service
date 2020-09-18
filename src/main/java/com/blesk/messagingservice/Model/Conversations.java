package com.blesk.messagingservice.Model;

import com.blesk.messagingservice.Value.Messages;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "conversations")
public class Conversations {

    @Id
    private String conversationId;

    @Version
    private Long version;

    @Valid
    @NotNull(message = Messages.CONVERSATION_PARTICIPANTS_NOT_NULL)
    private Set<Users> participants = new HashSet<>();

    private Boolean isDeleted = false;

    private Date createdAt = new Date();

    private Date updatedAt = null;

    private Date deletedAt = null;

    public Conversations() {
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Set<Users> getParticipants() {
        return this.participants;
    }

    public void setParticipants(Set<Users> participants) {
        this.participants = participants;
    }

    public Boolean getDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public static class Users {

        @NotNull(message = Messages.CONVERSATION_USERS_ACCOUNT_ID_NOT_NULL)
        private Long accountId;

        @DBRef
        @NotNull(message = Messages.CONVERSATION_STATUS_ID_NOT_NULL)
        private Status status;

        private String lastConversationId = null;

        private String lastReadedConversationId = null;

        public Users() {
        }

        public Long getAccountId() {
            return this.accountId;
        }

        public void setAccountId(Long accountId) {
            this.accountId = accountId;
        }

        public Status getStatus() {
            return this.status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public String getLastConversationId() {
            return this.lastConversationId;
        }

        public void setLastConversationId(String lastConversationId) {
            this.lastConversationId = lastConversationId;
        }

        public String getLastReadedConversationId() {
            return this.lastReadedConversationId;
        }

        public void setLastReadedConversationId(String lastReadedConversationId) {
            this.lastReadedConversationId = lastReadedConversationId;
        }
    }
}