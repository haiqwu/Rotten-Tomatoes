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

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo checkUser(String username, Byte[] password) {
        UserInfo user = userRepository.findByUserNameAndPassW(username, password);
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
        if(findUser == null){
            //throw new ChangeSetPersister.NotFoundException("NOT EXIST!");
        }
        BeanUtils.copyProperties(user,findUser);
        return userRepository.save(user);
    }

    @Override
    public Page<UserInfo> listUser(Long movieId, Pageable pageable) {
        return userRepository.findAll(new Specification<UserInfo>() {
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("wantsToSeeList");
                return cb.equal(join.get("id"),movieId);
            }
        },pageable);
    }
}
