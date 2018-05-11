package com.peppa.peppamovies.dao;

import com.peppa.peppamovies.model.MovieInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MovieRepository extends JpaRepository<MovieInfo,Long> {
    @Query("select m from MovieInfo m where LOWER(m.movieName) like LOWER(?1) ")
    Page<MovieInfo> findByQuery(String query, Pageable pageable);

    @Query("select m from MovieInfo m where m.releasedDate >= ?1 ")
    Page<MovieInfo> findByDate(Date date, Pageable pageable);

    @Query("select m from MovieInfo m where m.releasedDate >= ?1 and m.releasedDate <= ?2")
    Page<MovieInfo> findByTwoDate(Date dateStart, Date dateEnd, Pageable pageable);
    @Query("select m from MovieInfo m order by m.totalRate desc")
    Page<MovieInfo> findByRate(Pageable pageable);
}
