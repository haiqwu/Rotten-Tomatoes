package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.UserInfo;

public interface UserService {
    UserInfo checkUser(String username, String password);
}
