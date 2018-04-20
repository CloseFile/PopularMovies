package ctapk.popularmovies.utill;

import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ctapk.popularmovies.R;
import ctapk.popularmovies.data.MovieContract;
import ctapk.popularmovies.model.Movie;

import static ctapk.popularmovies.data.MovieContract.MovieEntry.*;


public class Parser {

    private static List<Movie> movieList;

    public static List<Movie> getMoviesFromJson(String jsonSource, Context context) throws JSONException {
        movieList = new ArrayList<>();

        JSONObject rootObj = new JSONObject(jsonSource);
        JSONArray moviesJsonArray = rootObj.getJSONArray(context.getString(R.string.jsonNames_results));

        for (int i = 0; i < moviesJsonArray.length(); i++) {
            JSONObject jsonMovie = moviesJsonArray.getJSONObject(i);
            movieList.add(new Movie(
                    jsonMovie.getInt(context.getString(R.string.jsonNames_movie_id)),
                    jsonMovie.getString(context.getString(R.string.jsonNames_title)),
                    jsonMovie.getString(context.getString(R.string.jsonNames_poster_path)),
                    jsonMovie.getString(context.getString(R.string.jsonNames_backdropPath_path)),
                    jsonMovie.getString(context.getString(R.string.jsonNames_overview)),
                    jsonMovie.getString(context.getString(R.string.jsonNames_vote_average)),
                    jsonMovie.getString(context.getString(R.string.jsonNames_release_date))));
        }
        return movieList;
    }

//    public static List<Movie> fetchMovieData(String requestUrl) throws JSONException {
//        URL url = createUrl(requestUrl);
//        String jsonResponse = null;
//        try {
//            jsonResponse = getResponseFromHttpUrl(url);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
//        }
//        List<Movie> movieList = MovieDbJSONUtils.getMovieDetailsFromJson(jsonResponse);
//        return movieList;
//    }
//
//    public static List<Review> fetchMovieReview(String requestUrl) throws JSONException {
//        URL url = createUrl(requestUrl);
//        String jsonResponse = null;
//        try {
//            jsonResponse = getResponseFromHttpUrl(url);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
//        }
//        List<Review> movieReviewsList = null;
//        movieReviewsList = MovieDbJSONUtils.getReviewFromJSON(jsonResponse);
//        return movieReviewsList;
//    }
//
//    public static List<Trailer> fetchMovieTrailer(String requestUrl) throws JSONException {
//        URL url = createUrl(requestUrl);
//        String jsonResponse = null;
//        try {
//            jsonResponse = getResponseFromHttpUrl(url);
//        } catch (IOException e) {
//            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
//        }
//        List<Trailer> movieTrailerList;
//        movieTrailerList = MovieDbJSONUtils.getTrailerFromJSON(jsonResponse);
//        return movieTrailerList;
//    }

    public static List<Movie> getMoviesFromCursor(Context context) {

        movieList = new ArrayList<>();

        String[] FAVORITES_PROJECTION = {
                _ID,
                COLUMN_MOVIE_ID,
                COLUMN_TITLE,
                COLUMN_BACKDROP_PATH,
                COLUMN_OVERVIEW,
                COLUMN_RELEASE_DATE,
                COLUMN_VOTE_AVERAGE
        };

        Cursor mCursor = context.getContentResolver().query(CONTENT_URI,
                FAVORITES_PROJECTION,
                null,
                null,
                null);

//        int primaryKeyColumnIndex = mCursor.getColumnIndex(MovieContract.MovieEntry._ID);
//        int idColumnIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
//        int titleColumnIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
//        int posterColumnIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
//        int backdropColumnIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH);
//        int releasedDateColumnIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);
//        int synopsisColumnIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
//        int voteColumnIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE);
//        mCursor != null &&
        if ( mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                int primaryKeyID = mCursor.getInt(mCursor.getColumnIndex(_ID));
                int currentID = mCursor.getInt(mCursor.getColumnIndex(COLUMN_MOVIE_ID));
                String currentTitle = mCursor.getString(mCursor.getColumnIndex(COLUMN_TITLE));
                String currentPosterPath = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
                String currentBackdropPath = mCursor.getString(mCursor.getColumnIndex(COLUMN_BACKDROP_PATH));
                String currentReleaseDate = mCursor.getString(mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
                String currentSynopsis = mCursor.getString(mCursor.getColumnIndex(COLUMN_OVERVIEW));
                String currentVoteAverage = mCursor.getString(mCursor.getColumnIndex(COLUMN_VOTE_AVERAGE));

                movieList.add(new Movie(currentID, currentTitle, currentPosterPath, currentBackdropPath,
                        currentSynopsis, currentVoteAverage, currentReleaseDate));
            }
        }

        mCursor.close();
        return movieList;
    }


}