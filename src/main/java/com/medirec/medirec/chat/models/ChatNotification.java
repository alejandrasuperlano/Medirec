package com.medirec.medirec.chat.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatNotification {
    private String id;
    private int senderId;
    private String senderName;
}
