package com.weiqilab.hackathon.hackathonkit.callbacks;

import com.weiqilab.hackathon.hackathonkit.pojo.Movie;

import java.util.ArrayList;

/**
 * Created by Windows on 13-04-2015.
 */
public interface UpcomingMoviesLoadedListener {
    public void onUpcomingMoviesLoaded(ArrayList<Movie> listMovies);
}
