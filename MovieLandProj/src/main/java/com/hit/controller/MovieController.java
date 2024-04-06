package com.hit.controller;

import com.hit.service.MovieService;

public class MovieController {

    private MovieService m_movieService;

    public MovieController(MovieService movieService) {
        this.m_movieService = movieService;
    }

    //TODO : make some methods as the service
}
