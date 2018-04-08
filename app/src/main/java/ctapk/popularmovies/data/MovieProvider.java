package ctapk.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {

    public static final int CODE_MOVIES = 0;
    public static final int CODE_MOVIE = 1;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.AUTHORITY;

        /* This URI is content://com.example.android.sunshine/weather/ */
        matcher.addURI(authority, MovieContract.PATH_MOVIES, CODE_MOVIES);

        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/#", CODE_MOVIE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            // All movies in database.
            case CODE_MOVIES: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                return cursor;
            }
            // Movie with id.
            case CODE_MOVIE: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieContract.MovieEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return cursor;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

//       cursor.setNotificationUri(getContext().getContentResolver(), uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in PopularMovies.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        if (sUriMatcher.match(uri) != CODE_MOVIES)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
            long rowID = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
//        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
//
//        // Write URI matching code to identify the match for the tasks directory
//        int match = sUriMatcher.match(uri);
//        Uri returnUri; // URI to be returned
//
//        switch (match) {
//            case TASKS:
//                // Insert new values into the database
//                // Inserting values into tasks table
//                long id = db.insert(TABLE_NAME, null, values);
//                if (id > 0) {
//                    returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
//                } else {
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                }
//                break;
//            // Set the value for the returnedUri and write the default case for unknown URI's
//            // Default case throws an UnsupportedOperationException
//            default:
//                throw new UnsupportedOperationException("Unknown uri: " + uri);
//        }
//
//        // Notify the resolver if the uri has been changed, and return the newly inserted URI
//        getContext().getContentResolver().notifyChange(uri, null);
//
//        // Return constructed uri (this points to the newly inserted row of data)
//        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int numRowsDeleted;

        switch (match) {
            case CODE_MOVIE:
                String id = uri.getPathSegments().get(1);
                numRowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new RuntimeException("We are not implementing update in PopularMovies");
    }


}
