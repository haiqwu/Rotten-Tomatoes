package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieReview;

import java.util.Comparator;

public class ReviewComparator implements Comparator<MovieReview> {
    public int compare(MovieReview self, MovieReview other) {
        return self.compareTo(other);
    }
}
