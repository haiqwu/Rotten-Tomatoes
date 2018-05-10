package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    UserInfo checkUser(String username, String password);

    boolean checkUsername(String username);

    UserInfo getUserByUserName(String username);

    boolean registeredEmail(String email);

    UserInfo saveUser(UserInfo user);

    UserInfo getUser(Long id);

    UserInfo updateUser(Long id, UserInfo user);

    void deleteUser(Long id);
}
