package com.mimo.messengerserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> messages;

    public ChatRoom() {}

    public ChatRoom(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}