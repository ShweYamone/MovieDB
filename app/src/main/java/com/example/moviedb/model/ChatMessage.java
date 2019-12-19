package com.example.moviedb.model;

import com.example.moviedb.common.Pageable;

import java.io.Serializable;

public class ChatMessage implements Serializable, Pageable {
    private String messageId;
    private String messageText;
    private String messageUser;
    private String messageTime;
    private String isDeleted;
    private Long accountId;
    private String isPhoto;

    public ChatMessage() {
    }


    public ChatMessage(String messageText, String messageUser, String messageTime, String isDeleted, Long accountId,String isPhoto) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
        this.isDeleted = isDeleted;
        this.accountId = accountId;
        this.isPhoto = isPhoto;
    }

    public String isPhoto() {
        return isPhoto;
    }

    public void setPhoto(String photo) {
        isPhoto = photo;
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
