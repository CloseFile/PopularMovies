package myapk.ru.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

class MovieLoader extends AsyncTaskLoader<List<Movie>> {

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
        String sortOrder = preferences.getString(getContext().getString(R.string.sort_by_key_pref),
                getContext().getString(R.string.preference_order_defult_value));

        try {

            URL moviesURL = NetworkUtils.buildMoviesURL(sortOrder);
            String responseFromHttpUrl = NetworkUtils.getResponse(moviesURL);
            return mMovieList = JsonParser.getMoviesFromJson(responseFromHttpUrl, getContext());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(@Nullable List<Movie> data) {
        mMovieList = data;
        super.deliverResult(data);
    }
}
