package com.peppa.peppamovies.dao;

import com.peppa.peppamovies.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo,Long>{
    UserInfo findByUserNameAndPassW(String username, Byte[] password);
}