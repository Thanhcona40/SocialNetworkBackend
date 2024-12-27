package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Message message);

    List<Message> getMessages(Long senderId, Long receiverId);
}
