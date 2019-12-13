package com.example.moviedb.model;

import com.example.moviedb.common.Pageable;

import java.io.Serializable;

public class ChatMessage implements Serializable, Pageable {
    private String messageText;
    private String messageUser;
    private String messageTime;
    private Long accountId;

    public ChatMessage() {
    }


    public ChatMessage(String messageText, String messageUser, String messageTime, Long accountId) {

        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
        this.accountId = accountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

}
