package ctapk.popularmovies.utill;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static String buildImageURL(String path, String imageSize) {

        Uri requestUri = Uri.parse(Constants.IMAGE_BASE_URL).buildUpon()
                .appendPath(imageSize)
                .build();

        return requestUri.toString() + path;
    }


    public static URL buildMoviesURL(String sortOrder) throws MalformedURLException {

        final String API_KEY_PARAM = "api_key";

        Uri moviesUri = Uri.parse(Constants.MOVIES_BASE_URL).buildUpon()
                .appendEncodedPath(sortOrder)
                .appendQueryParameter(API_KEY_PARAM, Constants.MOVIE_DB_API_KEY)
                .build();

        return new URL(moviesUri.toString());

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

}

