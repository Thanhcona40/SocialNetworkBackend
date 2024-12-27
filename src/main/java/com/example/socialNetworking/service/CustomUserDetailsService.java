package com.example.socialNetworking.service;

import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser =  userRepository.findByEmail(username);

        if(findUser == null){
            throw new UsernameNotFoundException("user name not found with email");
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(findUser.getEmail(), findUser.getPassword(), authorityList);
    }
}
