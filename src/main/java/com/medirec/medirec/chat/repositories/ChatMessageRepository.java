package com.medirec.medirec.chat.repositories;

import java.util.List;

import javax.transaction.Transactional;

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
            String senderId, String recipientId, String status);

    List<ChatMessage> findByChatId(String chatId);

    @Modifying
    @Transactional
    @Query(
        value = "UPDATE chat_message SET status = :messageStatus WHERE sender_id = :senderId AND recipient_id = :recipientId",
        nativeQuery = true
    )
    public void updateStatuses(
        @Param("messageStatus") String status,
        @Param("senderId") String senderId,
        @Param("recipientId") String recipientId
    );
}
