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
}
