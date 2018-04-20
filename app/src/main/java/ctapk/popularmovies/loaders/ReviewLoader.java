package ctapk.popularmovies.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import ctapk.popularmovies.model.Review;
import ctapk.popularmovies.utill.NetworkUtils;
import ctapk.popularmovies.utill.Parser;

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {
    private List<Review> mReviewList;
    private int movieId;

    public ReviewLoader(Context context, int movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (mReviewList != null) {
            deliverResult(mReviewList);
        }else {
            forceLoad();
        }
    }

    @Override
    public List<Review> loadInBackground() {
        try {

            URL reviewUrl = NetworkUtils.buildReviewUrl(String.valueOf(movieId));
            String responseFromHttpUrl = NetworkUtils.getResponse(reviewUrl);

            return mReviewList = Parser.getReviews(responseFromHttpUrl, getContext());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void deliverResult(List<Review> data) {
        mReviewList = data;
        super.deliverResult(data);
    }
}