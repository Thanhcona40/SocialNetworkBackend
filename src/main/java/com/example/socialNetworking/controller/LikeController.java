package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.LikeDto;
import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.mapper.LikeMapper;
import com.example.socialNetworking.dto.mapper.PostsMapper;
import com.example.socialNetworking.model.Like;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.service.LikeService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/likes/")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("/like/{postId}")
    public ResponseEntity<LikeDto> likePosts(@PathVariable("postId") Long postId, @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Like like = likeService.likePosts(postId, user);
        LikeDto likeDto = LikeMapper.Instance.likeToLikeDto(like, PostsMapper.INSTANCE,user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(like.getPosts(),user);
        likeDto.setPostsDto(postsDto);
        
        return new ResponseEntity<>(likeDto, HttpStatus.OK);
    }
}
