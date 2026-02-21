package com.mimo.messengerserver;

import org.antlr.v4.runtime.misc.NotNull;

public class MessageDTO {

    private String content;
    private String sender;
    private Long roomId;

    MessageDTO() {}

    public String getContent() {
        return this.content;
    }
    public String getSender() {
        return this.sender;
    }
    public Long getRoomId() {
        return this.roomId;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
