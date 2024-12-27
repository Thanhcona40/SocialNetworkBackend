package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Like;
import com.example.socialNetworking.model.User;

public interface LikeService {
    Like likePosts(Long postId, User user);

    Like getALlLikes(Long postId);
}
