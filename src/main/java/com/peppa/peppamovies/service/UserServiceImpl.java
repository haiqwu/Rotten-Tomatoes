package com.peppa.peppamovies.service;

import com.peppa.peppamovies.dao.UserRepository;
import com.peppa.peppamovies.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo checkUser(String username, String password) {
        UserInfo user = userRepository.findByUserNameAndPassW(username,password);
        return user;
    }
}
