package com.logistic.logic.e_saloon.CustomerChat;

public class ChatMessage {
    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";

    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";

    String messageContent;
    String timeStamp;
    String messageType;

    public ChatMessage() {
    }

    public ChatMessage(String messageContent, String timeStamp, String messageType) {
        this.messageContent = messageContent;
        this.timeStamp = timeStamp;
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
