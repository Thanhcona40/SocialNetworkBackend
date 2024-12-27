package com.example.socialNetworking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private String image;
    private boolean isComment;
    private boolean isPosts;
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @ManyToMany
    private List<Posts> commentPosts = new ArrayList<>();

    @ManyToMany
    private List<User> shareUser = new ArrayList<>();

    @ManyToMany
    private List<User> bookmarkUser = new ArrayList<>();
}
