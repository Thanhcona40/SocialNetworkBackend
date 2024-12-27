package com.example.socialNetworking.service;

import com.example.socialNetworking.exception.PostsException;
import com.example.socialNetworking.model.Like;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.LikeRepository;
import com.example.socialNetworking.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostsRepository postsRepository;
    private final PostsService postsService;

    @Override
    public Like likePosts(Long postId, User user) throws PostsException {
        Like existedLike = likeRepository.findByPostsIdAndUserId(postId, user.getId());

        if(existedLike != null){
            likeRepository.deleteById(existedLike.getId());
            return existedLike;
        }

        Posts posts = postsService.findById(postId);

        Like like = new Like();
        like.setPosts(posts);
        like.setUser(user);

        Like savedLike = likeRepository.save(like);
        posts.getLikes().add(like);
        postsRepository.save(posts);

        return savedLike;
    }

    @Override
    public Like getALlLikes(Long postId) {
        return likeRepository.findByPostsId(postId);
    }
}
