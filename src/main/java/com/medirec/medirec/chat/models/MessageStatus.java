package com.medirec.medirec.chat.models;

public enum MessageStatus {
    // RECEIVED = 0, el mensaje llega al server y es enviado al destino, pero no es ha sido leído
    // DELIVERED = 1, el mensaje ha sido leído
    RECEIVED, DELIVERED
}
