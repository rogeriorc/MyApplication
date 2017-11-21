package com.example.roger.myapplication;

import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class MovieList {

    public static final String MOVIE_CATEGORY[] = {
            "female",
            "male",
            "couple",
            "shemale"
    };

    private static Movie buildMovieInfo(int id,
                                        String name,
                                        String description,
                                        String cardImageUrl) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setUsername(name);
        movie.setDescription(description);
        movie.setImageUrl(cardImageUrl);

        return movie;
    }

    public static void get(final int id, final ArrayObjectAdapter mRowsAdapter) {
        final String category = MOVIE_CATEGORY[id];
        //final String url = "http://pt.cam4.com/directoryCams?json=true&gender=" + category;
        final String url = "https://pt.cam4.com/directoryCams?directoryJson=true&online=true&url=true&gender=" + category;
        //final String url = "http://pt.cam4.com/directoryFanClubs?category=" + category;


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Movie> list = new ArrayList<>();
                            //JSONObject result = new JSONObject(response.getString("results"));
                            //JSONArray users = result.getJSONArray("users");
                            JSONArray users = response.getJSONArray("users");

                            //Log.i("results", result.toString());


                            for (int i = 0; i < users.length(); i++) {
                                JSONObject user = users.optJSONObject(i);
                                /*
                                int gender = user.getInt("gender") - 1;
                                if (gender != id) {
                                    Log.i("GENDER", user.getString("username") + ": " + String.valueOf(gender + 1));

                                    continue;
                                }
                                */


                                JSONArray thumbs = new JSONArray(user.getString("snapshotThumbnailIds"));
                                String image = "";

                                if (thumbs.length() > 0)
                                    //image = "http://images2.cam4s.com/avatar/200x150/" + thumbs.getString(0) + ".jpg";
                                    image = "https://imgs2.cam4.com/avatar/400x300/" + thumbs.getString(0) + ".jpg";

                                Movie movie = buildMovieInfo(
                                        i,
                                        user.getString("username"),
                                        user.getString("statusMessage"),
                                        image);

                                list.add(movie);
                            }

                            Collections.sort(list, new Comparator<Movie>() {
                                @Override
                                public int compare(Movie lhs, Movie rhs) {
                                    return lhs.getViewers() - rhs.getViewers();
                                }
                            });

                            ((ArrayObjectAdapter) ((ListRow) mRowsAdapter.get(id)).getAdapter()).addAll(0, list);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        Cam4Application.getInstance().addToRequestQueue(jsObjRequest);
    }
}
