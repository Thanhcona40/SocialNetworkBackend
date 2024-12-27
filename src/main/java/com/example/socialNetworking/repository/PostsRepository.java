package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByIsPostsTrueOrderByCreatedAtDesc();

    List<Posts> findAllByShareUserContainsOrUser_IdAndIsPostsTrueOrderByCreatedAtDesc(User shareUser, Long userId);


}
