package ctapk.popularmovies.utill;


import ctapk.popularmovies.BuildConfig;

public class Constants {
    static final String MOVIE_DB_API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    final static String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie";
    final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
//    final static String IMAGE_SIZE = "w185";
    public final static String SORT_ORDER_POPULAR = "popular";
    public final static String SORT_ORDER_HIGHEST_RATED = "top_rated";

    public final static String SORT_ORDER_FAVORITE = "favorite";

     static final String YOUTUBE_VIDEO_PATH = "watch";
     static final String YOUTUBE_VIDEO_Query_Parameter = "v";
     static final String YOUTUBE_IMAGE_PATH = "vi";
     static final String YOUTUBE_IMAGE_POSTFIX = "0.jpg";

     static final String YOUTUBE_VIDEO_BASE_URL = "https://www.youtube.com";
     static final String YOUTUBE_IMAGE_BASE_URL = "https://img.youtube.com";

     static final String DEFAULT_REVIEW_MD_LINK = "http://api.themoviedb.org/3/movie/";
     static final String YOUTUBE_LINK_VIDEO = "https://www.youtube.com/watch?v=";
}
