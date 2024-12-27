package com.example.socialNetworking.dto.mapper;

import com.example.socialNetworking.dto.MessageDto;
import com.example.socialNetworking.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface MessageMapper {

    MessageMapper Instance = Mappers.getMapper(MessageMapper.class);

    MessageDto messageToMessageDto(Message message);
}
