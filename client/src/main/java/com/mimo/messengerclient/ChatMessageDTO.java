package com.mimo.messengerclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ChatMessageDTO {
    private String user;
    private String content;

    @JsonCreator
    public ChatMessageDTO(@JsonProperty("user") String user, @JsonProperty("content") String content) {
        this.user = user;
        this.content = content;
    }

    public String getUser() { return user; }
    public String getContent() { return content; }

    public void setUser(String user) { this.user = user; }
    public void setContent(String content) { this.content = content; }
}
