package com.peppa.peppamovies.service;

import com.peppa.peppamovies.dao.MovieReviewRepository;
import com.peppa.peppamovies.model.MovieReview;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MovieReviewServiceImpl implements MovieReviewService {
    @Autowired
    private MovieReviewRepository movieReviewRepository;

    @Transactional
    @Override
    public MovieReview saveMovieReview(MovieReview movieReview) {
        return movieReviewRepository.save(movieReview);
    }

    @Transactional
    @Override
    public MovieReview getMovieReview(Long id) {
        return movieReviewRepository.getOne(id);
    }
    @Transactional
    @Override
    public MovieReview updateMovieReview(Long id, MovieReview movieReview) {
        MovieReview findMovieReview = movieReviewRepository.getOne(id);
        if(findMovieReview == null){
            //throw new ChangeSetPersister.NotFoundException("NOT EXIST!");
        }
        BeanUtils.copyProperties(movieReview,findMovieReview);
        return movieReviewRepository.save(movieReview);
    }
    @Transactional
    @Override
    public void deleteMovieReview(Long id) {
        movieReviewRepository.deleteById(id );
    }
}
