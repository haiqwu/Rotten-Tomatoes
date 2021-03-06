package com.peppa.peppamovies.service;

import com.peppa.peppamovies.dao.UserRepository;
import com.peppa.peppamovies.model.UserInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo checkUser(String username, String password) {
        UserInfo user = userRepository.findByUserNameAndPassW(username, password);
        return user;
    }

    @Override
    public boolean checkUsername(String username) {
        UserInfo user = userRepository.findByUserName(username);
        if (user == null) {
            return true;
        }
        return false;
    }

    @Override
    public UserInfo getUserByUserName(String username) {
        UserInfo user = userRepository.findByUserName(username);
        return user;
    }

    @Override
    public boolean registeredEmail(String email) {
        return false;
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
        if (findUser == null) {
            //throw new ChangeSetPersister.NotFoundException("NOT EXIST!");
        }
        BeanUtils.copyProperties(user, findUser);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.delete( getUser(id) );
    }

    @Override
    public List<UserInfo> getReportedUsers() {
        return userRepository.getAllReportedUsers();

    }

    @Override
    public List<UserInfo> getCriticApplyers() {
        return userRepository.getAllCriticApplyers();
    }

    @Override
    public Page<UserInfo> listCriticsByReview(Pageable pageable) {
        return userRepository.getAllCriticUsers(pageable);
    }


}
