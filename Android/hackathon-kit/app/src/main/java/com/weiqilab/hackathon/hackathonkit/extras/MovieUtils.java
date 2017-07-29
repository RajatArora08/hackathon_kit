package com.weiqilab.hackathon.hackathonkit.extras;

import com.android.volley.RequestQueue;
import com.weiqilab.hackathon.hackathonkit.database.DBMovies;
import com.weiqilab.hackathon.hackathonkit.json.Endpoints;
import com.weiqilab.hackathon.hackathonkit.json.Parser;
import com.weiqilab.hackathon.hackathonkit.json.Requestor;
import com.weiqilab.hackathon.hackathonkit.MyApplication;
import com.weiqilab.hackathon.hackathonkit.pojo.Movie;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windows on 02-03-2015.
 * make the request and load the data from parser, and write the local database
 */
public class MovieUtils {
    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue) {

        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlBoxOfficeMovies(30));
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDatabase().insertMovies(DBMovies.BOX_OFFICE, listMovies, true);
        return listMovies;
    }

    public static ArrayList<Movie> loadUpcomingMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlUpcomingMovies(30));
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDatabase().insertMovies(DBMovies.UPCOMING, listMovies, true);
        return listMovies;
    }
}
