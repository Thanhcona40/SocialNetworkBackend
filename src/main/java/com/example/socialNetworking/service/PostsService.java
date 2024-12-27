package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.request.PostsRequest;

import java.util.List;

public interface PostsService {
    Posts createPosts(Posts posts, User user);

    List<Posts> getAllPosts();

    List<Posts> getUserPosts(Long userId);

    Posts findById(Long postId);

    Posts createCommentPosts(PostsRequest reqPosts, User user);
}
