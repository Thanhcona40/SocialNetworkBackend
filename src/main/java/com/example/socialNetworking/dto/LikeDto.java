package com.example.socialNetworking.dto;

import lombok.Data;

@Data
public class LikeDto {
    private Long id;

    private PostsDto postsDto;

    private UserDto userDto;
}
