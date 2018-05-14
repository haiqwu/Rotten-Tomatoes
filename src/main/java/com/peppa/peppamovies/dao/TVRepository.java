package com.peppa.peppamovies.dao;


import com.peppa.peppamovies.model.TVInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;

public interface TVRepository extends JpaRepository<TVInfo,Long> {

    @Query("select m from TVInfo m where LOWER(m.tvName) like LOWER(?1) ")
    Page<TVInfo> findByQuery(String query, Pageable pageable);

//    @Query("select m from MovieInfo m where m.releasedDate >= ?1 ")
//    Page<MovieInfo> findByDate(Date date, Pageable pageable);

//    @Query("select m from MovieInfo m where m.releasedDate >= ?1 and m.releasedDate <= ?2")
//    Page<MovieInfo> findByTwoDate(Date dateStart, Date dateEnd, Pageable pageable);
    @Query("select m from TVInfo m order by m.totalRate desc")
    Page<TVInfo> findAllTopRate(Pageable pageable);

    @Query("select m from TVInfo m order by m.criticRate desc")
    Page<TVInfo> findAllCriticTopRate(Pageable pageable);

    @Query("select m from TVInfo m where m.season = ?1 and m.tvName = ?2")
    TVInfo findTVBySeason(int seasonNum, String movieName);
}
