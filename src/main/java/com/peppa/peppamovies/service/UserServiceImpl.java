package com.peppa.peppamovies.service;

import com.peppa.peppamovies.dao.UserRepository;
import com.peppa.peppamovies.model.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo checkUser(String username, Byte[] password) {

        UserInfo user = userRepository.findByUserNameAndPassW(username,password);
        return user;
    }
    @Transactional

    @Override
    public UserInfo saveUser(UserInfo user) {
        return userRepository.save(user);
    }
    @Transactional
    @Override
    public UserInfo getUser(Long id) {
        return userRepository.getOne(id);
    }
    @Transactional
    @Override
    public UserInfo updateUser(Long id, UserInfo user) {
        UserInfo findUser = userRepository.getOne(id);
        if(findUser == null){
            //throw new ChangeSetPersister.NotFoundException("NOT EXIST!");
        }
        BeanUtils.copyProperties(user,findUser);
        return userRepository.save(user);
    }
    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id );
    }
}
