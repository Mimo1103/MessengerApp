package com.mimo.messengerserver;

import com.mimo.messengerserver.repository.ChatRoomRepository;
import com.mimo.messengerserver.entity.ChatRoom;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ChatRoomRepository chatRoomRepository;

    DataLoader(ChatRoomRepository repo) {
        this.chatRoomRepository = repo;
    }

    @Override
    public void run(String... args) {
        if (chatRoomRepository.count() == 0) {
            chatRoomRepository.save(new ChatRoom("1"));
            chatRoomRepository.save(new ChatRoom("2"));
            chatRoomRepository.save(new ChatRoom("3"));
            chatRoomRepository.save(new ChatRoom("4"));

        }
    }
}
