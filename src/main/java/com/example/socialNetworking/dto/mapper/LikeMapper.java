package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.LikeDto;
import com.example.socialNetworking.model.Like;
import com.example.socialNetworking.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(uses = {UserMapper.class, PostsMapper.class})
public interface LikeMapper {

    LikeMapper Instance = Mappers.getMapper(LikeMapper.class);

    @Mapping(target = "postsDto",  expression = "java(postsMapper.postsToPostsDto(like.getPosts(), req_user))")
    @Mapping(target = "userDto", source = "user")
    LikeDto likeToLikeDto(Like like,@Context PostsMapper postsMapper, @Context User req_user);

}
