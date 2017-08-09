package com.bitland.studioghibli;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Movie mMovie;
    private String description;

    @BindView(R.id.headerLabel)TextView mHeaderLabel;
    @BindView(R.id.titleLabel)TextView mTitleLabel;
    @BindView(R.id.movieLabel)TextView mMovieLabel;
    @BindView(R.id.producerLabel)TextView mProducerLabel;
    @BindView(R.id.producerNameLabel)TextView mProducerNameLabel;
    @BindView(R.id.directorLabel)TextView mDirectorLabel;
    @BindView(R.id.directorNameLabel)TextView mDirectorNameLabel;
    @BindView(R.id.yearOfReleaseLabel)TextView mYearOfRelease;
    @BindView(R.id.actualYearLabel)TextView mActualYearOfRelease;
    @BindView(R.id.ratingLabel)TextView mRatingLabel;
    @BindView(R.id.actualRatingLabel)TextView mActualRatingLabel;
    @BindView(R.id.showSymopsisButton)Button mShowSynopsisButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getMovie();

        mShowSynopsisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showSynopsis(description);
            }
        });


    }

    private void showSynopsis(String description) {
        Intent intent = new Intent(this, SynopsisActivity.class);

        Resources resources = getResources();
        String key = resources.getString(R.string.key);
        intent.putExtra(key, description);
        startActivity(intent);

    }

    private void getMovie() {
        String apiKey = "58611129-2dbc-4a81-a72f-77ddfc1b1b49";
        String studioGhibliUrl = "https://ghibliapi.herokuapp.com/films/" + apiKey + "/";

        if(isNetworkAvailable()){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(studioGhibliUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG,jsonData );
                    if(response.isSuccessful()){
                       mMovie = getMovieDetails(jsonData);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                upDateMovieDetails(mMovie);
                            }
                        });
                    } else {
                        alertUserAboutError();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                } catch (JSONException jse){
                    Log.e(TAG, "Exception caught: ", jse);
                }

            }
        });

        } else {

            NetworkDialogFragment dialog = new NetworkDialogFragment();
            dialog.show(getFragmentManager(),"network-error");

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    private void upDateMovieDetails(Movie movie) {
        mMovieLabel.setText(movie.getTitle());
        //mActualDescription.setText(movie.getDescription());
        mProducerNameLabel.setText(movie.getProducer());
        mDirectorNameLabel.setText(movie.getDirector());
        mActualYearOfRelease.setText(movie.getYearOfRelease() + "");
        mActualRatingLabel.setText(movie.getRatings() + "");

    }

    private Movie getMovieDetails(String jsonData) throws JSONException {
        JSONObject movie = new JSONObject(jsonData);
        String movieTitle = movie.getString("title");
        description = movie.getString("description");
        String director = movie.getString("director");
        String producer = movie.getString("producer");
        int releaseDate = movie.getInt("release_date");
        int rating = movie.getInt("rt_score");

        Movie theMovie = new Movie();
        theMovie.setTitle(movieTitle);
        theMovie.setProducer(producer);
        theMovie.setDirector(director);
        theMovie.setYearOfRelease(releaseDate);
        theMovie.setRatings(rating);


        return theMovie;
    }
}
