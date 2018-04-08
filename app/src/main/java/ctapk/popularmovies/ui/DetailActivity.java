package ctapk.popularmovies.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ctapk.popularmovies.model.Movie;
import ctapk.popularmovies.data.MovieContract;
import ctapk.popularmovies.utill.NetworkUtils;
import ctapk.popularmovies.R;


public class DetailActivity extends AppCompatActivity {

    Context mContext;
    private ImageView posterIV, backgroundIV;
    private TextView titleTV, synopsisTV, ratingTV, dateTV;
    Movie parcelledMovie;
    private boolean isLargeLayout=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //       setContentView(R.layout.activity_detail);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
         final ConstraintSet constraintSet1 = new ConstraintSet();
         final ConstraintSet constraintSet2 = new ConstraintSet();
        constraintSet2.clone(this, R.layout.activity_movie_transition);
        setContentView(R.layout.activity_detail);

        final ConstraintLayout constraintLayout=findViewById(R.id.constraintLayout);
        constraintSet1.clone(constraintLayout);



        backgroundIV = findViewById(R.id.background_iv);
        posterIV = findViewById(R.id.poster_iv);
//        titleTV = findViewById(R.id.background_iv);
        synopsisTV = findViewById(R.id.synopsis_tv);
        ratingTV = findViewById(R.id.rating_tv);
        dateTV = findViewById(R.id.date_tv);

        synopsisTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(constraintLayout);
                if (isLargeLayout) {
                    constraintSet1.applyTo(constraintLayout);
                } else {
                    constraintSet2.applyTo(constraintLayout);
                }
                isLargeLayout = !isLargeLayout;
            }
        });

        //get MovieItem from intent and set to views
        Intent intent = getIntent();

        parcelledMovie = intent.getParcelableExtra("parcelledMovie");
        setItemToViews(parcelledMovie);

        backgroundIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setItemToViews(Movie movieItem) {
        Picasso.with(mContext).load(NetworkUtils.buildImageURL(movieItem.getmBackdropPath(), "w500"))
                .into(backgroundIV);
        Picasso.with(mContext).load(NetworkUtils.buildImageURL(movieItem.getPosterPath(), "w185"))
                .into(posterIV);
//        titleTV.setText(movieItem.getTitle());
        synopsisTV.setText(movieItem.getOverview());
        ratingTV.setText(movieItem.getVoteAverage());
        dateTV.setText(movieItem.getReleaseDate());
    }


    public void insertData() {
//        String dateFormatted = convertDateFormat(mMovieDetails.getmReleaseDate());

        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, parcelledMovie.getmMovieTMDBId());
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, parcelledMovie.getTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, parcelledMovie.getPosterPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, parcelledMovie.getmBackdropPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, parcelledMovie.getOverview());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, parcelledMovie.getReleaseDate());
        movieValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, parcelledMovie.getVoteAverage());

        getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movieValues);
        Toast.makeText(getApplicationContext(), "Added " + movieValues.size() + "items.", Toast.LENGTH_LONG).show();

    }
}
