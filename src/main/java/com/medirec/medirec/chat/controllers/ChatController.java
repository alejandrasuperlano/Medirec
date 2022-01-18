package com.medirec.medirec.chat.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.chat.models.ChatMessage;
import com.medirec.medirec.chat.models.ChatNotification;
import com.medirec.medirec.chat.models.MessageStatus;
import com.medirec.medirec.chat.service.ChatMessageService;
import com.medirec.medirec.chat.service.ChatRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatRoomService chatRoomService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        Optional<String> chatId = chatRoomService.getChatId(
                chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        chatMessage.setChatId(chatId.get());

        chatMessage.setStatus(MessageStatus.RECEIVED.toString());

        ChatMessage saved = chatMessageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(chatMessage.getRecipientId()), "/queue/messages",
                new ChatNotification(saved.getId(), saved.getSenderId(),
                        saved.getSenderName()));
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(@PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{senderId}/{recipientId}/all")
    public ResponseEntity<Response> findChatMessages(
            @PathVariable String senderId, @PathVariable String recipientId) {
        Response response;
        List<ChatMessage> messages = new ArrayList<>();

        try {
            messages = chatMessageService.findChatMessages(senderId,
                    recipientId);
        } catch (Exception e) {
            response = new Response(HttpStatus.BAD_REQUEST.toString(),
                    "No se pudo encontrar el chat asociado a los id's de los usuarios",
                    messages);
            return new ResponseEntity<Response>(response,
                    HttpStatus.BAD_REQUEST);
        }

        response = new Response(HttpStatus.OK.toString(),
                "Estos son todos los mensajes", messages);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/messages/{senderId}/{recipientId}/new")
    public ResponseEntity<Response> findNewChatMessages(
            @PathVariable String senderId, @PathVariable String recipientId) {
        Response response;
        List<ChatMessage> messages = new ArrayList<>();

        try {
            messages = chatMessageService.findNewChatMessages(senderId,
                    recipientId);
        } catch (Exception e) {
            response = new Response(HttpStatus.BAD_REQUEST.toString(),
                    "No se pudo encontrar el chat asociado a los id's de los usuarios",
                    messages);
            return new ResponseEntity<Response>(response,
                    HttpStatus.BAD_REQUEST);
        }

        response = new Response(HttpStatus.OK.toString(),
                "Estos son los últimos mensajes", messages);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

}
