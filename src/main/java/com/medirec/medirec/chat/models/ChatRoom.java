package com.medirec.medirec.chat.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ChatRoom {
    @Id
    private String id;
    private String chatId;
    private int senderId;
    private int recipientId;
}
