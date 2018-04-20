package ctapk.popularmovies.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import ctapk.popularmovies.adapters.ImagesAdapter;
import ctapk.popularmovies.loaders.MovieLoader;
import ctapk.popularmovies.utill.Constants;
import ctapk.popularmovies.model.Movie;
import ctapk.popularmovies.R;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Movie>>,
        ImagesAdapter.ImageAdapterClickHandler {

    private final String SORT_POPULAR = Constants.SORT_ORDER_POPULAR;
    private final String SORT_RATED = Constants.SORT_ORDER_HIGHEST_RATED;
    private final String SORT_FAVORITE = Constants.SORT_ORDER_FAVORITE;

    private static final int MOVIES_LOADER = 22;
    RecyclerView movieGrid;
    String valuePreference;

    SharedPreferences preferences;

    private ProgressBar mLoadingIndicator;
    private ImagesAdapter mAdapter;

    List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set start value of preference:  "order by popularity"
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences == null) {
            valuePreference = SORT_POPULAR;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getString(R.string.sort_by_key_pref), valuePreference);
            editor.commit();
        }

        mLoadingIndicator = findViewById(R.id.loading_indicator);

        movieGrid = findViewById(R.id.movie_grid_rv);
        movieGrid.setLayoutManager(new GridLayoutManager(this, 2));
        movieGrid.setHasFixedSize(true);
        mAdapter = new ImagesAdapter(this, movieList, this);

        movieGrid.setAdapter(mAdapter);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<List<Movie>> managerLoader = loaderManager.getLoader(MOVIES_LOADER);

        if (managerLoader == null) {
            loaderManager.initLoader(MOVIES_LOADER, null, this);
        } else {
            loaderManager.restartLoader(MOVIES_LOADER, null, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (valuePreference==SORT_FAVORITE) getSupportLoaderManager()
                .restartLoader(MOVIES_LOADER,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_sort_popular:
                if (valuePreference != SORT_POPULAR) {
                    valuePreference = SORT_POPULAR;

                    preferences.edit()
                            .putString(getString(R.string.sort_by_key_pref), valuePreference)
                            .apply();
                    getSupportLoaderManager().getLoader(MOVIES_LOADER).forceLoad();
                }
                return true;
            case R.id.action_sort_top_rated:
                if (valuePreference != SORT_RATED) {
                    valuePreference = SORT_RATED;

                    preferences.edit()
                            .putString(getString(R.string.sort_by_key_pref), valuePreference)
                            .apply();
                    getSupportLoaderManager().getLoader(MOVIES_LOADER).forceLoad();
                }
                return true;
            case R.id.action_favorite:
                if (valuePreference != SORT_FAVORITE) {
                    valuePreference = SORT_FAVORITE;

                    preferences.edit()
                            .putString(getString(R.string.sort_by_key_pref), valuePreference)
                            .apply();
                    getSupportLoaderManager().getLoader(MOVIES_LOADER).forceLoad();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle args) {

        return new MovieLoader(this);

    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);

        if (null == data) {
            Toast.makeText(this, "Couldn't fetch the data", Toast.LENGTH_LONG).show();
        } else {
            mAdapter.setMovieData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
    }

    @Override
    public void onClickMethod(Movie parcelledMovie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("parcelledMovie", parcelledMovie);
        startActivity(intent);
    }
}