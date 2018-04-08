package ctapk.popularmovies.loaders;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ctapk.popularmovies.utill.Constants;
import ctapk.popularmovies.model.Movie;
import ctapk.popularmovies.utill.NetworkUtils;
import ctapk.popularmovies.utill.Parser;
import ctapk.popularmovies.R;


public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private List<Movie> mMovieList;

    public MovieLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {

        if (mMovieList != null) {
            deliverResult(mMovieList);
        } else {
            forceLoad();
        }
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String sortBy = preferences.getString(getContext().getString(R.string.sort_by_key_pref),
                getContext().getString(R.string.preference_order_defult_value));

        if (sortBy.equals(Constants.SORT_ORDER_HIGHEST_RATED) || sortBy.equals(Constants.SORT_ORDER_POPULAR)) {


            try {

                URL moviesURL = NetworkUtils.buildMoviesURL(sortBy);
                String responseFromHttpUrl = NetworkUtils.getResponse(moviesURL);

                return mMovieList = Parser.getMoviesFromJson(responseFromHttpUrl, getContext());

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        try {

            return mMovieList = Parser.getMoviesFromCursor(getContext());

        } catch (Exception e) {
            Toast.makeText(getContext(), "Couldn't fetch the data from database", Toast.LENGTH_LONG).show();
//                Log.e(TAG, "Failed to asynchronously load data.");
            e.printStackTrace();
            return null;
        }
    }

        @Override
        public void deliverResult (@Nullable List < Movie > data) {
            mMovieList = data;
            super.deliverResult(data);
        }
    }
