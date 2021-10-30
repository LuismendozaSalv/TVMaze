package com.mendosal.tvmaze.ui;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

public class MyShowRecyclerViewAdapter extends RecyclerView.Adapter<MyShowRecyclerViewAdapter.ViewHolder> {

    private List<ShowEntity> mValues;
    Context ctx;

    public MyShowRecyclerViewAdapter(Context context, List<ShowEntity> items) {
        mValues = items;
        ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitle.setText(holder.mItem.getName());
        holder.rbAverage.setRating(holder.mItem.getRating().getAverage());
        holder.tvGenres.setText(holder.mItem.getGenres().toString());
        Glide.with(ctx)
                .load(holder.mItem.getImage().getMedium())
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    public void setShowList(List<ShowEntity> showList) {
        mValues = showList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitle;
        public final ImageView ivPoster;
        public final RatingBar rbAverage;
        public final TextView tvGenres;
        public ShowEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitle = view.findViewById(R.id.tvShowTitle);
            ivPoster = view.findViewById(R.id.ivShowPoster);
            rbAverage = view.findViewById(R.id.rbAverage);
            tvGenres = view.findViewById(R.id.tvShowGenre);
        }
    }
}