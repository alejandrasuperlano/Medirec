package com.medirec.medirec.chat.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @JsonIgnore
    private int id;
    
    @JsonIgnore
    private String chatId;

    private String senderId;
    private String recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    private Date timestamp;
    
    @JsonIgnore
    private String status;
}
