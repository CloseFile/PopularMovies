package ctapk.popularmovies.utill;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    static final String API_KEY_PARAM = "api_key";

    public static String buildImageURL(String path, String imageSize) {

        Uri requestUri = Uri.parse(Constants.IMAGE_BASE_URL).buildUpon()
                .appendPath(imageSize)
                .build();

        return requestUri.toString() + path;
    }


    public static URL buildMoviesURL(String sortOrder) throws MalformedURLException {

        Uri moviesUri = Uri.parse(Constants.MOVIES_BASE_URL).buildUpon()
                .appendEncodedPath(sortOrder)
                .appendQueryParameter(API_KEY_PARAM, Constants.MOVIE_DB_API_KEY)
                .build();

        return new URL(moviesUri.toString());
    }

    public static URL buildReviewUrl(String movieId) throws MalformedURLException {

        Uri movieReviewUri = Uri.parse(Constants.MOVIES_BASE_URL).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath("reviews")
                .appendQueryParameter(API_KEY_PARAM, Constants.MOVIE_DB_API_KEY)
                .build();

        return new URL(movieReviewUri.toString());
    }
    public static URL buildVideoUrl(String movieId) throws MalformedURLException {

        Uri movieVTrailerUri = Uri.parse(Constants.MOVIES_BASE_URL).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath("videos")
                .appendQueryParameter(API_KEY_PARAM, Constants.MOVIE_DB_API_KEY)
                .build();

        return new URL(movieVTrailerUri.toString());
    }

    public static String getResponse(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream stream = connection.getInputStream();

        try {
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");

            return scanner.hasNext() ? scanner.next() : null;
        } finally {
            if (stream != null) {
                stream.close();
            }
            connection.disconnect();
        }
    }

    public static String youTubeVideoUrlBuilder(String key) {
        return Uri.parse(Constants.YOUTUBE_VIDEO_BASE_URL)
                .buildUpon()
                .appendEncodedPath(Constants.YOUTUBE_VIDEO_PATH)
                .appendQueryParameter(Constants.YOUTUBE_VIDEO_Query_Parameter, key)
                .build().toString();
    }

    public static String youTubeImageUrlBuilder(String key) {
        return Uri.parse(Constants.YOUTUBE_IMAGE_BASE_URL)
                .buildUpon()
                .appendEncodedPath(Constants.YOUTUBE_IMAGE_PATH)
                .appendEncodedPath(key)
                .appendEncodedPath(Constants.YOUTUBE_IMAGE_POSTFIX)
                .build().toString();
    }

}

