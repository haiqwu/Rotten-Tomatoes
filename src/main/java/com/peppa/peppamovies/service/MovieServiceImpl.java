package com.peppa.peppamovies.service;

import com.peppa.peppamovies.dao.MovieRepository;
import com.peppa.peppamovies.model.MovieInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    @Override
    public MovieInfo saveMovie(MovieInfo movie) {

        return movieRepository.save(movie);
    }

    @Transactional
    @Override
    public MovieInfo getMovie(Long id) {
        return movieRepository.getOne(id);
    }

    @Transactional
    @Override
    public MovieInfo updateMovie(Long id, MovieInfo movie) {
        MovieInfo findMovie = movieRepository.getOne(id);
        if (findMovie == null) {
            //throw new ChangeSetPersister.NotFoundException("NOT EXIST!");
        }
        BeanUtils.copyProperties(movie, findMovie);
        return movieRepository.save(movie);
    }

    @Override
    public Page<MovieInfo> listMovie(String query, Pageable pageable) {
        return movieRepository.findByQuery(query, pageable);
    }

    @Transactional
    @Override
    public Page<MovieInfo> listOpeningMovie(Date dateStart, Date dateEnd, Pageable pageable) {
        return movieRepository.findByTwoDate(dateStart, dateEnd, pageable);
    }

    @Transactional
    @Override
    public Page<MovieInfo> listComing(Date date, Pageable pageable) {
        return movieRepository.findByDate(date, pageable);
    }

    @Transactional
    @Override
    public void deleteMovie(Long id) {
        movieRepository.delete( getMovie(id)   );
    }


    @Transactional
    @Override
    public Page<MovieInfo> listTopMovie(Pageable pageable) {
        return movieRepository.findByBoxOffice(pageable);
    }

    @Transactional
    @Override
    public Page<MovieInfo> listRateMovie(Pageable pageable) {
        return movieRepository.findByRate(pageable);
    }

}
