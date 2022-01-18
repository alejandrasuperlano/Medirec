package com.medirec.medirec.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.medirec.medirec.chat.models.ChatMessage;
import com.medirec.medirec.chat.models.MessageStatus;
import com.medirec.medirec.chat.repositories.ChatMessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {
    
    @Autowired private ChatMessageRepository repository;
    @Autowired private ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED.toString());
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(String senderId, String recipientId) {
        return repository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) throws Exception{
        Optional<String> chatIdOptional = chatRoomService.getChatId(senderId, recipientId, false);
        if(!chatIdOptional.isPresent()){
            throw new Exception("No se pudo encontrar el chat");
        }

        List<ChatMessage> messages =
                chatIdOptional.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());

        if(messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {

        repository.updateStatuses(status.toString(), senderId, recipientId);
    }
}
