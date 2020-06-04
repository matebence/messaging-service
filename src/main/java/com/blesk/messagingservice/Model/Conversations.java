package com.blesk.messagingservice.Model;

import com.blesk.messagingservice.Value.Messages;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
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

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    private Timestamp updatedAt = null;

    private Timestamp deletedAt = null;

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

    public static class Users {

        @NotNull(message = Messages.CONVERSATION_USERS_ACCOUNT_ID_NOT_NULL)
        private Long accountId;

        @NotNull(message = Messages.CONVERSATION_STATUS_ID_NOT_NULL)
        private String statusId;

        @NotNull(message = Messages.CONVERSATION_USERS_USER_NAME_NOT_NULL)
        private String userName;

        private String lastConversionId = null;

        private String lastReadedConversionId = null;

        public Users() {
        }

        public Long getAccountId() {
            return this.accountId;
        }

        public void setAccountId(Long accountId) {
            this.accountId = accountId;
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

        public String getLastConversionId() {
            return this.lastConversionId;
        }

        public void setLastConversionId(String lastConversionId) {
            this.lastConversionId = lastConversionId;
        }

        public String getLastReadedConversionId() {
            return this.lastReadedConversionId;
        }

        public void setLastReadedConversionId(String lastReadedConversionId) {
            this.lastReadedConversionId = lastReadedConversionId;
        }
    }
}