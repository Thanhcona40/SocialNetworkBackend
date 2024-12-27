package com.example.socialNetworking.request;

import lombok.Data;


@Data
public class PostsRequest {
    private Long postId;
    private String content;
    private String image;
}
