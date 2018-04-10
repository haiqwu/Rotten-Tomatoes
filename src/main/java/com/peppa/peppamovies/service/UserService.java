package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.UserInfo;

public interface UserService {
    UserInfo checkUser(String username, Byte[] password);

    UserInfo saveUser(UserInfo user);

    UserInfo getUser(Long id);

    UserInfo updateUser(Long id, UserInfo user);

    void deleteUser(Long id);
}
