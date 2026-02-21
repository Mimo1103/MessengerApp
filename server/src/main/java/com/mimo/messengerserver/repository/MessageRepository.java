package com.mimo.messengerserver.repository;

import com.mimo.messengerserver.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findTop20ByChatRoomIdOrderByIdDesc(int roomId);
}
