package myapk.ru.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Context mContext;
    private ImageView posterIV;
    private TextView titleTV, synopsisTV, ratingTV, dateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        posterIV = findViewById(R.id.poster_iv);
        titleTV = findViewById(R.id.title_tv);
        synopsisTV = findViewById(R.id.synopsis_tv);
        ratingTV = findViewById(R.id.rating_tv);
        dateTV = findViewById(R.id.date_tv);

        //get MovieItem from intent and set to views
        Intent intent = getIntent();
        Movie parcelledMovie = intent.getParcelableExtra("parcelledMovie");
        setItemToViews(parcelledMovie);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setItemToViews(Movie movieItem) {
        Picasso.with(mContext).load(NetworkUtils.buildImageURL(movieItem.getPosterPath()))
                .into(posterIV);
        titleTV.setText(movieItem.getTitle());
        synopsisTV.setText(movieItem.getOverview());
        ratingTV.setText(Float.toString(movieItem.getVoteAverage()) );
        dateTV.setText(movieItem.getReleaseDate());
    }
}
