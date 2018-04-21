package com.peppa.peppamovies.dao;

import com.peppa.peppamovies.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {
    UserInfo findByUserNameAndPassW(String username, Byte[] password);
}