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
    @Query("select m from MovieInfo m order by m.box_office desc")
    Page<MovieInfo> findByRate(Pageable pageable);


//    @Query("insert into MovieInfo (audiance_rate, box_office, brief_intro, \n" +
//            "critic_rate, genres, movie_images, movie_name, movie_poster, movie_trailers, \n" +
//            "released_date, runtime_minutes, secondaryid, title_type, total_rate) values ( )")
//    void addMovie(double audiance_rate, int box_office,String brief_intro,
//                  double critic_rate, String genres,String movie_images,
//                  String movie_name,String movie_poster, String movie_trailers,
//                  Date released_date, int runtime_minutes,String secondaryid,
//                  String title_type,double total_rate);

}
