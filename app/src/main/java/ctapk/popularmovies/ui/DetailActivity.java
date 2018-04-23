package ctapk.popularmovies.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ctapk.popularmovies.adapters.ReviewAdapter;
import ctapk.popularmovies.adapters.TrailerAdapter;
import ctapk.popularmovies.loaders.ReviewLoader;
import ctapk.popularmovies.loaders.TrailerLoader;
import ctapk.popularmovies.model.Movie;
import ctapk.popularmovies.model.Review;
import ctapk.popularmovies.model.Trailer;
import ctapk.popularmovies.utill.NetworkUtils;
import ctapk.popularmovies.R;

import static ctapk.popularmovies.data.MovieContract.MovieEntry.*;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final int TRAILER_LOADER = 11;
    private static final int REVIEW_LOADER = 22;
    Context mContext;
    private ImageView posterIV;
    private TextView titleTV, ratingTV, dateTV;
    Movie parcelledMovie;
    private boolean isLargeLayout = false;
    FloatingActionButton fab;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;

    private List<String> movieReviews = new ArrayList<>();
    private List<Trailer> trailers = new ArrayList<>();

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

        final ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintSet1.clone(constraintLayout);


//        backgroundIV = findViewById(R.id.background_iv);
        posterIV = findViewById(R.id.poster_iv);
        titleTV = findViewById(R.id.title_tv);
        ratingTV = findViewById(R.id.rating_tv);
        dateTV = findViewById(R.id.date_tv);
        fab = findViewById(R.id.fab);


//        synopsisTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TransitionManager.beginDelayedTransition(constraintLayout);
//                if (isLargeLayout) {
//                    constraintSet1.applyTo(constraintLayout);
//                } else {
//                    constraintSet2.applyTo(constraintLayout);
//                }
//                isLargeLayout = !isLargeLayout;
//            }
//        });

        //get MovieItem from intent and set to views
        Intent intent = getIntent();

        parcelledMovie = intent.getParcelableExtra("parcelledMovie");
        setItemToViews(parcelledMovie);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite()) {
                    deleteItem();
                    fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                    Snackbar.make(v, "DELETE from DB", Snackbar.LENGTH_LONG).show();
                } else {
                    insertData();
                    fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                    Snackbar.make(v, "ADD to DB", Snackbar.LENGTH_LONG).show();

                }
            }
        });

        getSupportLoaderManager().initLoader(REVIEW_LOADER, null, this);
        RecyclerView reviewRecyclerView = findViewById(R.id.review_rv);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(this, movieReviews);
        reviewRecyclerView.setAdapter(reviewAdapter);

        getSupportLoaderManager().initLoader(TRAILER_LOADER, null, this);
        RecyclerView trailerRecyclerView = findViewById(R.id.trailer_rv);

        trailerRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        trailerRecyclerView.setHasFixedSize(true);
        // add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(trailerRecyclerView);
        trailerAdapter = new TrailerAdapter(this, trailers);
        trailerRecyclerView.setAdapter(trailerAdapter);
        trailerRecyclerView.addItemDecoration(new LinePagerIndicatorDecoration());


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFavorite()) {
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
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

        Picasso.with(mContext)
                .load(NetworkUtils.buildImageURL(movieItem.getPosterPath(), "w185"))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .into(posterIV);

        ratingTV.setText(movieItem.getVoteAverage());
        dateTV.setText(movieItem.getReleaseDate());
        titleTV.setText(movieItem.getTitle());

    }


    public void insertData() {

        ContentValues movieValues = new ContentValues();
        movieValues.put(COLUMN_MOVIE_ID, parcelledMovie.getmMovieTMDBId());
        movieValues.put(COLUMN_TITLE, parcelledMovie.getTitle());
        movieValues.put(COLUMN_POSTER_PATH, parcelledMovie.getPosterPath());
        movieValues.put(COLUMN_BACKDROP_PATH, parcelledMovie.getmBackdropPath());
        movieValues.put(COLUMN_OVERVIEW, parcelledMovie.getOverview());
        movieValues.put(COLUMN_RELEASE_DATE, parcelledMovie.getReleaseDate());
        movieValues.put(COLUMN_VOTE_AVERAGE, parcelledMovie.getVoteAverage());

        getContentResolver().insert(CONTENT_URI, movieValues);
    }

    private void deleteItem() {
        getContentResolver().delete(CONTENT_URI.buildUpon()
                .appendEncodedPath(String.valueOf(parcelledMovie.getmMovieTMDBId())).build(), null, null);
//        int numDeleted = getContentResolver().delete(CONTENT_URI,
//                (COLUMN_MOVIE_ID + "=" + parcelledMovie.getmMovieTMDBId()), null);
    }

    private boolean isFavorite() {

        Cursor cursor = getContentResolver().query(
                CONTENT_URI,
                null,
                COLUMN_MOVIE_ID + "=" + parcelledMovie.getmMovieTMDBId(),
                null,
                null);
        assert cursor != null;
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == TRAILER_LOADER) {
            return new TrailerLoader(this, parcelledMovie.getmMovieTMDBId());
        } else if (id == REVIEW_LOADER) {
            return new ReviewLoader(this, parcelledMovie.getmMovieTMDBId());
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        int id = loader.getId();
        if (id == TRAILER_LOADER) {
            trailerAdapter.setTrailerList((List<Trailer>) data);
        } else if (id == REVIEW_LOADER) {
            List<Review> reviews = (List<Review>) data;
            List<String> reviewContent = new ArrayList<>();

            if (reviews != null && reviews.size() > 0) {

                for (int i = 0; i < reviews.size(); i++) {
                    reviewContent.add(reviews.get(i).getContent());
                }
            } else {
                Toast.makeText(this, "No reviews", Toast.LENGTH_SHORT).show();
                //               emptyReviewTv.setVisibility(View.VISIBLE);
            }
            // Add overview as 1 element in RV
            reviewContent.add(0, parcelledMovie.getOverview());
            reviewAdapter.setReviews(reviewContent);
        }
    }


    @Override
    public void onLoaderReset(Loader loader) {
        int id = loader.getId();
        if (id == TRAILER_LOADER) {
            trailerAdapter.setTrailerList(null);
        } else if (id == REVIEW_LOADER) {
            reviewAdapter.setReviews(null);
        }
    }
}
