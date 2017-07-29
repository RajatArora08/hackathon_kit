package com.weiqilab.hackathon.hackathonkit.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import com.weiqilab.hackathon.hackathonkit.callbacks.UpcomingMoviesLoadedListener;
import com.weiqilab.hackathon.hackathonkit.extras.MovieUtils;
import com.weiqilab.hackathon.hackathonkit.network.VolleySingleton;
import com.weiqilab.hackathon.hackathonkit.pojo.Movie;

/**
 * Created by Windows on 02-03-2015.
 */
public class TaskLoadUpcomingMovies extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private UpcomingMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadUpcomingMovies(UpcomingMoviesLoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {

        ArrayList<Movie> listMovies = MovieUtils.loadUpcomingMovies(requestQueue);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> listMovies) {
        if (myComponent != null) {
            myComponent.onUpcomingMoviesLoaded(listMovies);
        }
    }
}
