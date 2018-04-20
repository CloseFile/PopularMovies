package ctapk.popularmovies.model;

public class Trailer {
//    private int movieId;
    private String key;
    private String name;
    private String site;


//    public int getMovieId() {
//        return movieId;
//    }

//    public void setMovieId(int movieId) {
//        this.movieId = movieId;
//    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setId(String site) {
        this.site = site;
    }

    public Trailer( String key, String name, String site) {
        this.key = key;
        this.name = name;
        this.site = site;
    }
}
