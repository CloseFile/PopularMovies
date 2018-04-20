package ctapk.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ctapk.popularmovies.R;

//public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private static final int TYPE_HEADER = 0;
//    private static final int TYPE_ITEM = 1;
//    String[] data;
//
//    public ReviewAdapter(String[] data) {
//        this.data = data;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == TYPE_ITEM) {
//            //inflate your layout and pass it to view holder
//            return new VHItem(null);
//        } else if (viewType == TYPE_HEADER) {
//            //inflate your layout and pass it to view holder
//            return new VHHeader(null);
//        }
//
//        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof VHItem) {
//            String dataItem = getItem(position);
//            //cast holder to VHItem and set data
//        } else if (holder instanceof VHHeader) {
//            //cast holder to VHHeader and set data for header.
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.length + 1;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (isPositionHeader(position))
//            return TYPE_HEADER;
//
//        return TYPE_ITEM;
//    }
//
//    private boolean isPositionHeader(int position) {
//        return position == 0;
//    }
//
//    private String getItem(int position) {
//        return data[position - 1];
//    }
//
//    class VHItem extends RecyclerView.ViewHolder {
//        TextView title;
//
//        public VHItem(View itemView) {
//            super(itemView);
//        }
//    }
//
//    class VHHeader extends RecyclerView.ViewHolder {
//        Button button;
//
//        public VHHeader(View itemView) {
//            super(itemView);
//        }
//    }
//}
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
//    private final String overview;
    private Context context;
    private List<String> reviews;

    public ReviewAdapter(Context context,List<String> reviews) {
        this.context = context;
        this.reviews=reviews;
//        this.overview=overview;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String currentItem = reviews.get(position);
        holder.reviewTv.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
 //       @BindView(R.id.tv_review)
        TextView reviewTv;

        public ViewHolder(View itemView) {
            super(itemView);
            reviewTv=itemView.findViewById(R.id.tv_review);
//            ButterKnife.bind(this, itemView);
        }
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }
}