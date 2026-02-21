package com.mimo.messengerserver.entity;

import jakarta.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String sender;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    public Message() {}

    public Message(String content, String sender, ChatRoom chatRoom) {
        this.content = content;
        this.sender = sender;
        this.chatRoom = chatRoom;
    }

    public Long getId() {
        return this.id;
    }
    public String getContent() {
        return this.content;
    }
    public String getSender() {
        return this.sender;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
}