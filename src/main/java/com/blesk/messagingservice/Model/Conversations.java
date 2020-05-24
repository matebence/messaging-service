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

    @Valid
    @NotNull(message = Messages.CONVERSATION_PARTICIPANTS_NOT_NULL)
    private Set<Users> participants = new HashSet<>();

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

    private static class Users{

        public static enum ConversationStatus {
            ONLINE, OFFLINE
        }

        @NotNull(message = Messages.CONVERSATION_USERS_ACCOUNT_ID_NOT_NULL)
        private Long accountId;

        @NotNull(message = Messages.CONVERSATION_USERS_USER_NAME_NOT_NULL)
        private String userName;

        @NotNull(message = Messages.CONVERSATION_USERS_STATUS_NOT_NULL)
        private ConversationStatus conversationStatus;

        public Users() {
        }

        public Long getAccountId() {
            return this.accountId;
        }

        public void setAccountId(Long accountId) {
            this.accountId = accountId;
        }

        public String getUserName() {
            return this.userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public ConversationStatus getConversationStatus() {
            return this.conversationStatus;
        }

        public void setConversationStatus(ConversationStatus conversationStatus) {
            this.conversationStatus = conversationStatus;
        }
    }
}