package com.weiqilab.hackathon.hackathonkit.json;

import com.weiqilab.hackathon.hackathonkit.extras.Constants;
import com.weiqilab.hackathon.hackathonkit.extras.Keys;
import com.weiqilab.hackathon.hackathonkit.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Weiqi Zhao on 02-03-2015.
 * Parser is used for parsing data from response
 */
public class Parser {
    //
    public static ArrayList<Movie> parseMoviesJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // array list to add movies
        ArrayList<Movie> listMovies = new ArrayList<>();

        // get the response and parse the related data
        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayMovies = response.getJSONArray(Keys.EndpointBoxOffice.KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    long id = -1;

                    // initialization for the data structure
                    String title = Constants.NA;
                    String releaseDate = Constants.NA;
                    int audienceScore = -1;
                    String synopsis = Constants.NA;
                    String urlThumbnail = Constants.NA;
                    String urlSelf = Constants.NA;
                    String urlCast = Constants.NA;
                    String urlReviews = Constants.NA;
                    String urlSimilar = Constants.NA;

                    JSONObject currentMovie = arrayMovies.getJSONObject(i);

                    //get the id of the current movie
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_ID)) {
                        id = currentMovie.getLong(Keys.EndpointBoxOffice.KEY_ID);
                    }

                    //get the title of the current movie
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_TITLE)) {
                        title = currentMovie.getString(Keys.EndpointBoxOffice.KEY_TITLE);
                    }

                    //get the date in theaters for the current movie
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_RELEASE_DATES)) {
                        JSONObject objectReleaseDates = currentMovie.getJSONObject(Keys.EndpointBoxOffice.KEY_RELEASE_DATES);

                        if (Utils.contains(objectReleaseDates, Keys.EndpointBoxOffice.KEY_THEATER)) {
                            releaseDate = objectReleaseDates.getString(Keys.EndpointBoxOffice.KEY_THEATER);
                        }
                    }

                    //get the audience score for the current movie

                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_RATINGS)) {
                        JSONObject objectRatings = currentMovie.getJSONObject(Keys.EndpointBoxOffice.KEY_RATINGS);
                        if (Utils.contains(objectRatings, Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE)) {
                            audienceScore = objectRatings.getInt(Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE);
                        }
                    }

                    // get the synopsis of the current movie
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_SYNOPSIS)) {
                        synopsis = currentMovie.getString(Keys.EndpointBoxOffice.KEY_SYNOPSIS);
                    }

                    //get the url for the thumbnail to be displayed inside the current movie result
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_POSTERS)) {
                        JSONObject objectPosters = currentMovie.getJSONObject(Keys.EndpointBoxOffice.KEY_POSTERS);

                        if (Utils.contains(objectPosters, Keys.EndpointBoxOffice.KEY_THUMBNAIL)) {
                            urlThumbnail = objectPosters.getString(Keys.EndpointBoxOffice.KEY_THUMBNAIL);
                        }
                    }

                    //get the url of the related links
                    if (Utils.contains(currentMovie, Keys.EndpointBoxOffice.KEY_LINKS)) {
                        JSONObject objectLinks = currentMovie.getJSONObject(Keys.EndpointBoxOffice.KEY_LINKS);
                        if (Utils.contains(objectLinks, Keys.EndpointBoxOffice.KEY_SELF)) {
                            urlSelf = objectLinks.getString(Keys.EndpointBoxOffice.KEY_SELF);
                        }
                        if (Utils.contains(objectLinks, Keys.EndpointBoxOffice.KEY_CAST)) {
                            urlCast = objectLinks.getString(Keys.EndpointBoxOffice.KEY_CAST);
                        }
                        if (Utils.contains(objectLinks, Keys.EndpointBoxOffice.KEY_REVIEWS)) {
                            urlReviews = objectLinks.getString(Keys.EndpointBoxOffice.KEY_REVIEWS);
                        }
                        if (Utils.contains(objectLinks, Keys.EndpointBoxOffice.KEY_SIMILAR)) {
                            urlSimilar = objectLinks.getString(Keys.EndpointBoxOffice.KEY_SIMILAR);
                        }
                    }

                    // set the data to movie object from parsed response
                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = null;
                    try {
                        date = dateFormat.parse(releaseDate);
                    } catch (ParseException e) {
                        //a parse exception generated here will store null in the release date, be sure to handle it
                    }
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);
                    movie.setUrlSelf(urlSelf);
                    movie.setUrlCast(urlCast);
                    movie.setUrlThumbnail(urlThumbnail);
                    movie.setUrlReviews(urlReviews);
                    movie.setUrlSimilar(urlSimilar);
//                    L.t(getActivity(), movie + "");

                    // if not null, add to object list
                    if (id != -1 && !title.equals(Constants.NA)) {
                        listMovies.add(movie);
                    }
                }

            } catch (JSONException e) {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        // return the list
        return listMovies;
    }


}
