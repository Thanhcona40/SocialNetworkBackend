package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Message;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.MessageRepository;
import com.example.socialNetworking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    private UserRepository userRepository;

    @Override
    public Message sendMessage(Message reqMess) {
        User sender = userRepository.findById(reqMess.getSender().getId()).orElseThrow();
        User receiver = userRepository.findById(reqMess.getReceiver().getId()).orElseThrow();

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(reqMess.getContent());
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(Long senderId, Long receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId,receiverId);
    }
}
