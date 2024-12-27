package com.example.socialNetworking.service;

import com.example.socialNetworking.exception.PostsException;
import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.PostsRepository;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.request.PostsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService{

    private final UserService userService;
    private final PostsRepository postsRepository;
    @Override
    public Posts createPosts(Posts posts, User user) {
        Posts newPosts = new Posts();
        newPosts.setImage(posts.getImage());
        newPosts.setContent(posts.getContent());
        newPosts.setUser(user);
        newPosts.setComment(false);
        newPosts.setPosts(true);
        newPosts.setCreatedAt(LocalDateTime.now());

        return postsRepository.save(newPosts);
    }



    @Override
    public List<Posts> getAllPosts() {
        return postsRepository.findAllByIsPostsTrueOrderByCreatedAtDesc();
    }

    @Override
    public List<Posts> getUserPosts(Long userId) {
        User user = userService.findById(userId);
        return postsRepository.findAllByShareUserContainsOrUser_IdAndIsPostsTrueOrderByCreatedAtDesc(user, userId);
    }

    @Override
    public Posts findById(Long postId) {
        return postsRepository.findById(postId).orElseThrow(() -> new PostsException("not found posts"));
    }

    @Override
    public Posts createCommentPosts(PostsRequest reqPosts, User user) {
        Posts posts = findById(reqPosts.getPostId());

        Posts comment = new Posts();
        comment.setContent(reqPosts.getContent());
        comment.setImage(reqPosts.getImage());
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setComment(true);
        comment.setPosts(false);

        postsRepository.save(comment);

        posts.getCommentPosts().add(comment);

        return postsRepository.save(posts);
    }


}
