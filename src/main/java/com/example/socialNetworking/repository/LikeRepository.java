package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByPostsIdAndUserId(Long postsId, Long userId);

    Like findByPostsId(Long postId);
}
