package com.medirec.medirec.chat.repositories;

import java.util.Optional;

import com.medirec.medirec.chat.models.ChatRoom;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends CrudRepository<ChatRoom, String>{
    
    Optional<ChatRoom> findBySenderIdAndRecipientId(int senderId, int recipientId);
}
