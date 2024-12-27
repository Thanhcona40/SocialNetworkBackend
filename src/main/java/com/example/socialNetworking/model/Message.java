package com.example.socialNetworking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime timestamp;

    private boolean IsSeen;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;


    @JsonIgnore
    @ManyToOne
    private ChatRoom chatRoom;
}
