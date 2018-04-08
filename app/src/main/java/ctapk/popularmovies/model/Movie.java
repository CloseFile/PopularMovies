package ctapk.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    /*{
      "vote_count": 7007,
      "id": 198663,
      "video": false,
      "vote_average": 7,
      "title": "The Maze Runner",
      "popularity": 396.91294,
      "poster_path": "/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
      "original_language": "en",
      "original_title": "The Maze Runner",
      "genre_ids": [
        28,
        9648,
        878,
        53
      ],
      "backdrop_path": "/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg",
      "adult": false,
      "overview": "Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow “runners” for a shot at escape.",
      "release_date": "2014-09-10"
    }*/
    private int mMovieTMDBId;
    private String title;
    private String posterPath;
    private String mBackdropPath;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    public Movie(int movieId, String title, String posterPath, String backdropPath, String overview,
                 String voteAverage, String releaseDate) {
        this.mMovieTMDBId = movieId;
        this.title = title;
        this.posterPath = posterPath;
        this.mBackdropPath = backdropPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public Movie(String title, String posterPath, String overview, String voteAverage, String releaseDate) {
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
        mBackdropPath = in.readString();
        mMovieTMDBId = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
        dest.writeString(mBackdropPath);
        //       dest.writeByte((byte) (mTrailerPath ? 1 : 0));
        dest.writeInt(mMovieTMDBId);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }


    public String getOverview() {
        return overview;
    }


    public String getVoteAverage() {
        return voteAverage;
    }


    public String getReleaseDate() {
        return releaseDate;
    }


    public String getmBackdropPath() {
        return mBackdropPath;
    }

//    public Boolean getmTrailerPath() {
//        return mTrailerPath;
//    }

    public int getmMovieTMDBId() {
        return mMovieTMDBId;
    }
}
