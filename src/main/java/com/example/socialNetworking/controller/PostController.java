package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.mapper.PostsMapper;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.request.PostsRequest;
import com.example.socialNetworking.service.PostsService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostsService postsService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<PostsDto> createPosts(@RequestHeader("Authorization") String jwt,
                                                @RequestBody Posts posts){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts createPost =  postsService.createPosts(posts, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(createPost, user);
        return new ResponseEntity<>(postsDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostsDto>> getAllPosts(@RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        List<Posts> posts = postsService.getAllPosts();
        List<PostsDto> postsDtos = new ArrayList<>();

        for(Posts post : posts){
            postsDtos.add(PostsMapper.INSTANCE.postsToPostsDto(post, user));
        }

        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @GetMapping("/{postsId}")
    public ResponseEntity<PostsDto> getPostsByPostsId(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long postsId){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = postsService.findById(postsId);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user);

        return new ResponseEntity<>(postsDto, HttpStatus.OK);

    }

    @GetMapping("/user/{postsId}")
    public ResponseEntity<List<PostsDto>> getUserPosts(@RequestHeader("Authorization") String jwt,
                                                       @PathVariable Long postsId){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        List<Posts> posts = postsService.getUserPosts(postsId);
        List<PostsDto> postsDtos = new ArrayList<>();

        for(Posts post : posts){
            postsDtos.add(PostsMapper.INSTANCE.postsToPostsDto(post, user));
        }

        return new ResponseEntity<>(postsDtos, HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<PostsDto> createCommmentPosts(@RequestHeader("Authorization") String jwt,
                                                        @RequestBody PostsRequest reqPosts) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Posts posts = postsService.createCommentPosts(reqPosts, user);
        PostsDto postsDto = PostsMapper.INSTANCE.postsToPostsDto(posts,user);

        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }
}
