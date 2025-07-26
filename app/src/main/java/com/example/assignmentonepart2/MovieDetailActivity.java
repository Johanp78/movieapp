package com.example.assignmentonepart2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignmentonepart2.databinding.ActivityMovieDetailBinding;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {

    ActivityMovieDetailBinding binding;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("title");
        String year = getIntent().getStringExtra("year");
        String posterUrl = getIntent().getStringExtra("poster");

        if (title == null || year == null || posterUrl == null) {
            Log.e("MovieDetail", "Missing intent data");
            finish();
            return;
        }

        binding.detailTitle.setText(title);
        binding.detailYear.setText(year);

        loadPosterImage(posterUrl);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadPosterImage(String url) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MovieDetail", "Image fetch failed", e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()) return;

                try (InputStream inputStream = response.body().byteStream()) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    runOnUiThread(() -> binding.posterImage.setImageBitmap(bitmap));
                } catch (Exception e) {
                    Log.e("MovieDetail", "Error decoding image", e);
                }
            }
        });
    }
}
