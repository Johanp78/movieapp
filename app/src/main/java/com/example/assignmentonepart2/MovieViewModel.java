package com.example.assignmentonepart2;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieViewModel extends ViewModel {

    private final MutableLiveData<List<MovieModel>> movieList = new MutableLiveData<>();

    public LiveData<List<MovieModel>> getMovieList() {
        return movieList;
    }

    public void searchMovies(String query) {
        String apiUrl = "https://www.omdbapi.com/?apikey=3ed5ddd1&s=" + query + "&type=movie";

        ApiClient.get(apiUrl, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("MovieViewModel", "API Request Failed", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) return;

                List<MovieModel> result = new ArrayList<>();
                try {
                    String responseData = response.body().string();
                    JSONObject json = new JSONObject(responseData);
                    JSONArray moviesArray = json.getJSONArray("Search");

                    for (int i = 0; i < moviesArray.length(); i++) {
                        JSONObject movieObj = moviesArray.getJSONObject(i);
                        MovieModel movie = new MovieModel();
                        movie.setTitle(movieObj.getString("Title"));
                        movie.setYear(movieObj.getString("Year"));
                        movie.setPoster(movieObj.getString("Poster"));
                        result.add(movie);
                    }

                    movieList.postValue(result);
                } catch (Exception e) {
                    Log.e("MovieViewModel", "Parsing Error", e);
                }
            }
        });
    }
}
