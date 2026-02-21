package com.mimo.messengerserver;

import com.mimo.messengerserver.entity.ChatRoom;
import com.mimo.messengerserver.entity.Message;
import com.mimo.messengerserver.repository.ChatRoomRepository;
import com.mimo.messengerserver.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    MessageController(MessageRepository messageRepository, ChatRoomRepository chatRoomRepository) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @PostMapping(path = "/send")
    public Message receiveMessage(@RequestBody() MessageDTO messageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(messageDTO.getRoomId()).orElseThrow();

        Message message = new Message(messageDTO.getContent(), messageDTO.getSender(), chatRoom);

        return messageRepository.save(message);
    }

    @GetMapping(path = "/receive")
    public List<ChatMessageDTO> sendMessage(@RequestParam int roomId) {
        List<Message> messages = messageRepository.findTop20ByChatRoomIdOrderByIdDesc(roomId);
        List<ChatMessageDTO> chatMessageDTOs = messages.stream()
                .map(msg -> new ChatMessageDTO(msg.getSender(), msg.getContent()))
                .toList();

        //Collections.reverse(chatMessageDTOs);
        return chatMessageDTOs;

    }
}
