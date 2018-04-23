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
}
