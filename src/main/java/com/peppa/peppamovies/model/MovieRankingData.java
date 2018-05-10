package com.peppa.peppamovies.model;

import java.util.ArrayList;
import java.util.List;


public class MovieRankingData {
    private List<MovieInfo> moviesOpeningThisWeek;
    private List<MovieInfo> topBoxMovies;
    private List<MovieInfo> comingSoonMovies;
    private List<MovieInfo> certifiedFreshMovies;
    private List<MovieInfo> topMovies;
    private List<MovieInfo> rollingBarMovies;
    private MovieInfo webBackgroundMovie;

    public MovieRankingData() {
        moviesOpeningThisWeek = new ArrayList<MovieInfo>();
        topBoxMovies = new ArrayList<MovieInfo>();
        comingSoonMovies = new ArrayList<MovieInfo>();
        certifiedFreshMovies = new ArrayList<MovieInfo>();
        topMovies = new ArrayList<MovieInfo>();
        rollingBarMovies = new ArrayList<MovieInfo>();
        webBackgroundMovie = new MovieInfo();
    }

    public List<MovieInfo> getMoviesOpeningThisWeek() {
        return moviesOpeningThisWeek;
    }

    public void setMoviesOpeningThisWeek(List<MovieInfo> moviesOpeningThisWeek) {
        this.moviesOpeningThisWeek = moviesOpeningThisWeek;
    }

    public List<MovieInfo> getTopBoxMovies() {
        return topBoxMovies;
    }

    public void setTopBoxMovies(List<MovieInfo> topBoxMovies) {
        this.topBoxMovies = topBoxMovies;
    }

    public List<MovieInfo> getComingSoonMovies() {
        return comingSoonMovies;
    }

    public void setComingSoonMovies(List<MovieInfo> comingSoonMovies) {
        this.comingSoonMovies = comingSoonMovies;
    }

    public List<MovieInfo> getCertifiedFreshMovies() {
        return certifiedFreshMovies;
    }

    public void setCertifiedFreshMovies(List<MovieInfo> certifiedFreshMovies) {
        this.certifiedFreshMovies = certifiedFreshMovies;
    }

    public List<MovieInfo> getTopMovies() {
        return topMovies;
    }

    public void setTopMovies(List<MovieInfo> topMovies) {
        this.topMovies = topMovies;
    }

    public List<MovieInfo> getRollingBarMovies() {
        return rollingBarMovies;
    }

    public void setRollingBarMovies(List<MovieInfo> rollingBarMovies) {
        this.rollingBarMovies = rollingBarMovies;
    }

    public MovieInfo getWebBackgroundMovie() {
        return webBackgroundMovie;
    }

    public void setWebBackgroundMovie(MovieInfo webBackgroundMovie) {
        this.webBackgroundMovie = webBackgroundMovie;
    }

    public ArrayList<long[]> loadMovieRankingData() {
        ArrayList<long[]> arr = new ArrayList<long[]>();
        long[] top_box_office_list = {1309, 2005, 2032, 2006, 2033, 733, 2056, 2057};//  2007, 2036
        long[] comming_soon = {2009, 2020, 2023, 2024, 2059, 2060, 2062, 2061};// 2029, 2031
        long[] opening_this_week = {2008, 2010, 2011, 2012, 2013, 2014, 2015, 2016};//, 2017, 2018
        long[] certified_fresh_movies = {1309, 2058, 2006, 2007, 2005, 2042, 2019, 2055};//, 2030, 2003
        arr.add(top_box_office_list);
        arr.add(comming_soon);
        arr.add(opening_this_week);
        arr.add(certified_fresh_movies);
        return arr;
    }
}
