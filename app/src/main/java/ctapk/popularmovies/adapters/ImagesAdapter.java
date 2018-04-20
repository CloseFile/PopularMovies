package ctapk.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ctapk.popularmovies.R;
import ctapk.popularmovies.model.Movie;
import ctapk.popularmovies.utill.NetworkUtils;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    private final ImageAdapterClickHandler mClickHandler;
    private final Context mContext;
    private List<Movie> mMovieList;

    public ImagesAdapter(Context c, List<Movie> movieList, ImageAdapterClickHandler
            imageAdapterClickHandler) {
        mContext = c;
        mMovieList = movieList;
        mClickHandler = imageAdapterClickHandler;
    }


    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {

        final Movie currentMovie = mMovieList.get(position);

        Picasso.with(mContext)
                .load(NetworkUtils.buildImageURL(currentMovie.getPosterPath(),"w185"))
                .error(R.drawable.ic_image_black_24dp)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (mMovieList != null) {
            return mMovieList.size();
        } else {
            return 0;
        }
    }

    public void setMovieData(List<Movie> movieItems) {
        mMovieList = movieItems;
        notifyDataSetChanged();
    }

    public interface ImageAdapterClickHandler {
        void onClickMethod(Movie movie);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView image;


        private ImageViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movie_poster_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movieItem = mMovieList.get(adapterPosition);
            mClickHandler.onClickMethod(movieItem);
        }
    }
}