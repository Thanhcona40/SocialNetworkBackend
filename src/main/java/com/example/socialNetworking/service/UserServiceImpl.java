package com.example.socialNetworking.service;

import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("not found user");
        }
        return user;
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->  new UserException("not found user"));
    }

    @Override
    public User updateUser(Long userId,User req) {
        User user = findById(userId);

        if(req.getFirstName() != null && !req.getFirstName().trim().isEmpty()) user.setFirstName(req.getFirstName());
        if(req.getLastName() != null && !req.getLastName().trim().isEmpty()) user.setLastName(req.getLastName());
        if(req.getImage() != null && !req.getImage().trim().isEmpty()) user.setImage(req.getImage());
        if(req.getBackgroundImage() != null && !req.getBackgroundImage().trim().isEmpty()) user.setBackgroundImage(req.getBackgroundImage());
        if(req.getDateOfBirth() != null) user.setDateOfBirth(req.getDateOfBirth()); // không cần kiểm tra isEmpty với Date
        if(req.getLocation() != null && !req.getLocation().trim().isEmpty()) user.setLocation(req.getLocation());
        if(req.getBio() != null && !req.getBio().trim().isEmpty()) user.setBio(req.getBio());
        if(req.getNumberPhone() != null && !req.getNumberPhone().trim().isEmpty()) user.setNumberPhone(req.getNumberPhone());

        return userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
