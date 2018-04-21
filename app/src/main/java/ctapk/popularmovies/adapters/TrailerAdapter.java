package ctapk.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ctapk.popularmovies.R;
import ctapk.popularmovies.model.Trailer;
import ctapk.popularmovies.utill.NetworkUtils;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private Context context;
    private List<Trailer> trailerList;

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        this.context = context;
        this.trailerList=trailerList;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {
        Trailer currentItem = trailerList.get(position);
        String ImageUrl = NetworkUtils.youTubeImageUrlBuilder(currentItem.getKey());
        Picasso.with(context)
                .load(ImageUrl)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .into(holder.trailerImageView);
        holder.trailer = currentItem;
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        }
        return trailerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView trailerImageView=itemView.findViewById(R.id.iv_trailer);

        private Trailer trailer;

        ViewHolder(View itemView) {
            super(itemView);
            itemView = itemView.findViewById(R.id.iv_trailer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String videoUrl = NetworkUtils.youTubeVideoUrlBuilder(trailer.getKey());
                    intent.setData(Uri.parse(videoUrl));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }
}
