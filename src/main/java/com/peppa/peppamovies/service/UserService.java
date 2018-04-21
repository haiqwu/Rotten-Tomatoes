package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserInfo checkUser(String username, Byte[] password);

    boolean registeredEmail(String email);

    UserInfo saveUser(UserInfo user);

    UserInfo getUser(Long id);

    UserInfo updateUser(Long id, UserInfo user);

    Page<UserInfo> listUser(Long Id, Pageable pageable);
}
