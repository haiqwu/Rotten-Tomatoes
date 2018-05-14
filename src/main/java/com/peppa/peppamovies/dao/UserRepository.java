package com.peppa.peppamovies.dao;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<UserInfo, Long>{
    UserInfo findByUserNameAndPassW(String username, String password);
    UserInfo findByUserName(String username);


    @Query("select m from UserInfo m where m.reported = 1 ")
    List<UserInfo> getAllReportedUsers();


    @Query("select m from UserInfo m where m.applying_critic = 1 ")
    List<UserInfo> getAllCriticApplyers();

    @Query("select m from UserInfo m where m.isCritic = 1 order by m.numFollowers desc ")
    Page<UserInfo> getAllCriticUsers(Pageable pageable);

}