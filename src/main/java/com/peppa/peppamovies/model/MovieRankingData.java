package com.peppa.peppamovies.model;

import java.util.ArrayList;


public class MovieRankingData {
    private ArrayList<MovieInfo> moviesOpeningThisWeek;
    private ArrayList<MovieInfo> topBoxMovies;
    private ArrayList<MovieInfo> comingSoonMovies;
    private ArrayList<MovieInfo> certifiedFreshMovies;
    private ArrayList<MovieInfo> topMovies;
    private ArrayList<MovieInfo> rollingBarMovies;
    private MovieInfo webBackgroundMovie;


    public MovieRankingData() {
        moviesOpeningThisWeek = new ArrayList<MovieInfo>();
        topBoxMovies= new ArrayList<MovieInfo>();
        comingSoonMovies =  new ArrayList<MovieInfo>();
        certifiedFreshMovies= new ArrayList<MovieInfo>();
        topMovies = new ArrayList<MovieInfo>();
        rollingBarMovies= new ArrayList<MovieInfo>();
        webBackgroundMovie = new MovieInfo();
    }


    public ArrayList<MovieInfo> getMoviesOpeningThisWeek() {
        return moviesOpeningThisWeek;
    }

    public void setMoviesOpeningThisWeek(ArrayList<MovieInfo> moviesOpeningThisWeek) {
        this.moviesOpeningThisWeek = moviesOpeningThisWeek;
    }

    public ArrayList<MovieInfo> getTopBoxMovies() {
        return topBoxMovies;
    }

    public void setTopBoxMovies(ArrayList<MovieInfo> topBoxMovies) {
        this.topBoxMovies = topBoxMovies;
    }

    public ArrayList<MovieInfo> getComingSoonMovies() {
        return comingSoonMovies;
    }

    public void setComingSoonMovies(ArrayList<MovieInfo> comingSoonMovies) {
        this.comingSoonMovies = comingSoonMovies;
    }

    public ArrayList<MovieInfo> getCertifiedFreshMovies() {
        return certifiedFreshMovies;
    }

    public void setCertifiedFreshMovies(ArrayList<MovieInfo> certifiedFreshMovies) {
        this.certifiedFreshMovies = certifiedFreshMovies;
    }

    public ArrayList<MovieInfo> getTopMovies() {
        return topMovies;
    }

    public void setTopMovies(ArrayList<MovieInfo> topMovies) {
        this.topMovies = topMovies;
    }

    public ArrayList<MovieInfo> getRollingBarMovies() {
        return rollingBarMovies;
    }

    public void setRollingBarMovies(ArrayList<MovieInfo> rollingBarMovies) {
        this.rollingBarMovies = rollingBarMovies;
    }

    public MovieInfo getWebBackgroundMovie() {
        return webBackgroundMovie;
    }

    public void setWebBackgroundMovie(MovieInfo webBackgroundMovie) {
        this.webBackgroundMovie = webBackgroundMovie;
    }

    public ArrayList<long[]> loadMovieRankingData()
    {
        ArrayList<long[]> arr = new ArrayList<long[]>();


        long[] top_box_office_list = {1309, 2005, 2032, 2006, 2033, 733, 2034, 2035 };//  2007, 2036
        long[] comming_soon = {2009, 2020, 2023, 2024, 2025, 2026, 2027, 2028};// 2029, 2031
        long[] opening_this_week = {2008, 2010, 2011, 2012, 2013, 2014, 2015, 2016};//, 2017, 2018
        long[] certified_fresh_movies = {1309, 2038, 2006, 2007, 2005, 2039, 2019, 2037};//, 2030, 2003

        arr.add(top_box_office_list);
        arr.add(comming_soon);
        arr.add(opening_this_week);
        arr.add(certified_fresh_movies);

        return arr;


    }
}
