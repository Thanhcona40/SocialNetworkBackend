package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.Status;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.request.LoginRequest;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user){
        //Tìm người dùng thông qua email
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser != null){
            throw new UserException("User already exist with another account");
        }
        //Mã hóa mật khẩu trước khi lưu vào csdl
        String password = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setStatus(Status.OFFLINE);
        userService.saveUser(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        String jwt = jwtUtils.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(jwt, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest){
        User findUser = userService.getUserByEmail(loginRequest.getEmail());
        if(findUser == null){
            throw new UserException("User not found in database");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        String jwt = jwtUtils.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(jwt, HttpStatus.CREATED);
    }
}
