package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.MessageDto;
import com.example.socialNetworking.model.Message;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.service.MessageService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private MessageService messageService;
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/user/chat")
    public ResponseEntity<Void> sendMessage( @RequestBody Message message){

        messageService.sendMessage(message);
        messagingTemplate.convertAndSendToUser(
                message.getReceiver().getFirstName() + message.getReceiver().getLastName(), "queue/messages",message
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{senderId}/{receiverId}")
    public List<Message> getMessages(@PathVariable Long senderId, @PathVariable Long receiverId){
        return messageService.getMessages(senderId,receiverId);
    }

}
