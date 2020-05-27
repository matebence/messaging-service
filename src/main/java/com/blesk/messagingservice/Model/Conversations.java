package com.blesk.messagingservice.Model;

import com.blesk.messagingservice.Value.Messages;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Document
public class Conversations {

    @Id
    private String conversationId;

    @Version
    private Long version;

    private Boolean isDeleted = false;

    @Valid
    @NotNull(message = Messages.CONVERSATION_PARTICIPANTS_NOT_NULL)
    private Set<Users> participants = new HashSet<>();

    public Conversations() {
    }

    public Boolean getDeleted() {
        return this.isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public static class Users{

        @NotNull(message = Messages.CONVERSATION_USERS_ACCOUNT_ID_NOT_NULL)
        private Long accountId;

        @NotNull(message = Messages.CONVERSATION_STATUS_ID_NOT_NULL)
        private String statusId;

        @NotNull(message = Messages.CONVERSATION_USERS_USER_NAME_NOT_NULL)
        private String userName;

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
    }
}