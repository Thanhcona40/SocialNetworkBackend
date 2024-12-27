package com.example.socialNetworking.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String location;
    private String dateOfBirth;
    private String email;
    private String numberPhone;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean req_user;

    private int totalFriends;
}
