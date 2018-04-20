package ctapk.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ctapk.popularmovies.model.Review;
import ctapk.popularmovies.model.Trailer;
import ctapk.popularmovies.utill.NetworkUtils;
import ctapk.popularmovies.utill.Parser;

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>> {
    private List<Trailer> mTrailerList;
    private int movieId;

    public TrailerLoader(Context context, int movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (mTrailerList != null) {
            deliverResult(mTrailerList);
        }else {
            forceLoad();
        }
    }

    @Override
    public List<Trailer> loadInBackground() {
        try {

            URL moviesURL = NetworkUtils.buildVideoUrl(String.valueOf(movieId));
            String responseFromHttpUrl = NetworkUtils.getResponse(moviesURL);

            return mTrailerList = Parser.getTrailers(responseFromHttpUrl, getContext());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void deliverResult(List<Trailer> data) {
        mTrailerList = data;
        super.deliverResult(data);
    }

}
