package com.example.socialNetworking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String location;
    private String dateOfBirth;
    private String email;
    private String password;
    private String numberPhone;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean req_user;
    private Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Posts> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @ManyToMany
    private List<User> friends = new ArrayList<>();
}
