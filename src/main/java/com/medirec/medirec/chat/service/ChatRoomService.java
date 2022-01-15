package com.medirec.medirec.chat.service;

import java.util.Optional;

import com.medirec.medirec.chat.models.ChatRoom;
import com.medirec.medirec.chat.repositories.ChatRoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {
    
    @Autowired ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(
        int senderId, int recipientId, boolean createIfNotExist) {

        return chatRoomRepository
            .findBySenderIdAndRecipientId(senderId, recipientId)
            .map(ChatRoom::getChatId)
                .or(() -> {
                if(!createIfNotExist) {
                    return  Optional.empty();
                }
                    String chatId =
                        String.format("%s_%s", senderId, recipientId);
                
                ChatRoom senderRecipient = new ChatRoom();
                senderRecipient.setRecipientId(recipientId);
                senderRecipient.setSenderId(senderId);
                senderRecipient.setChatId(chatId);

                ChatRoom recipientSender = new ChatRoom();
                recipientSender.setRecipientId(senderId);
                recipientSender.setSenderId(recipientId);
                recipientSender.setChatId(chatId);

                chatRoomRepository.save(senderRecipient);
                chatRoomRepository.save(recipientSender);

                return Optional.of(chatId);
            });
}}
