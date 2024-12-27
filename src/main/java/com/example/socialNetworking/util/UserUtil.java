package com.example.socialNetworking.util;

import com.example.socialNetworking.model.User;

public class UserUtil {
    public static final boolean isReqUser(User reqUser, User user2){
        return reqUser.getId().equals(user2.getId());
    }

    public static final boolean isFollowedByReqUser(User reqUser, User user2){
        return reqUser.getFriends().contains(user2);
    }
}
