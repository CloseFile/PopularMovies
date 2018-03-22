package myapk.ru.popularmovies;

import android.app.Application;

public class PopularMovieApplication extends Application {

    private static DBHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        db.DBHelper(getApplicationContext());
    }

    static public synchronized DBHelper getDb() {
        return db;
    }
}
