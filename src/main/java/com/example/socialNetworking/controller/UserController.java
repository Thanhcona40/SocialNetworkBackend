package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.UserDto;
import com.example.socialNetworking.dto.mapper.UserMapper;
import com.example.socialNetworking.model.Status;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.service.UserService;
import com.example.socialNetworking.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfileByJwt(@RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        UserDto userDto = UserMapper.Instance.userToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserProfileByUserId(@PathVariable Long id,
                                                          @RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User req_user = userService.getUserByEmail(email);
        User user = userService.findById(id);

        UserDto userDto = UserMapper.Instance.userToUserDto(user);
        userDto.setReq_user(UserUtil.isReqUser(user, req_user));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestHeader("Authorization") String jwt,
                                              @RequestBody User user){
        String email = jwtUtils.getEmailFromToken(jwt);
        User req = userService.getUserByEmail(email);

        User updateUser = userService.updateUser(req.getId(),user);

        UserDto userDto = UserMapper.Instance.userToUserDto(updateUser);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @MessageMapping("/userOnline")
    @SendTo("/user/status")
    public User updateUserOnline(@Header("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User req = userService.getUserByEmail(email);

        if(req.getStatus() == Status.ONLINE){
            req.setStatus(Status.OFFLINE);
        }else {
            req.setStatus(Status.ONLINE);
        }
        userService.saveUser(req);
        return req;
    }
}
