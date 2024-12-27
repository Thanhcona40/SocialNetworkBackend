package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.PostsDto;
import com.example.socialNetworking.dto.UserDto;
import com.example.socialNetworking.model.Posts;
import com.example.socialNetworking.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper
public interface UserMapper {
    UserMapper Instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "totalFriends", expression = "java(user.getFriends() != null ? user.getFriends().size() : 0)")
    @Mapping(target = "req_user", ignore = true)
    UserDto userToUserDto(User user);

    @AfterMapping
    default void setFullName(User user, @MappingTarget UserDto userDto) {
        userDto.setFullName(user.getFirstName() + " " + user.getLastName());
    }

}

