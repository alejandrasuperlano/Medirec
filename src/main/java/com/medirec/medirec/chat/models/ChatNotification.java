package com.medirec.medirec.chat.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatNotification {
    private int id;
    private String senderId;
    private String senderName;
}
