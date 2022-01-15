package com.medirec.medirec.chat.repositories;

import java.util.List;

import com.medirec.medirec.chat.models.ChatMessage;
import com.medirec.medirec.chat.models.MessageStatus;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, String>{
    long countBySenderIdAndRecipientIdAndStatus(
            int senderId, int recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);

    @Query(
        value = "UPDATE chatmessage SET status = :messageStatus WHERE senderId = :senderId AND recipientId = :recipientId",
        nativeQuery = true
    )
    @Modifying
    public void updateStatuses(
        @Param("messageStatus") String status,
        @Param("senderId") int senderId,
        @Param("recipientId") int recipientId
    );
}
