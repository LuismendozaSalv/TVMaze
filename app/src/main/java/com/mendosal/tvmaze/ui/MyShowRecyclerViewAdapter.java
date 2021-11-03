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
    private OnShowListener mOnShowListener;

    public MyShowRecyclerViewAdapter(Context context, List<ShowEntity> items, OnShowListener onShowListener) {
        mValues = items;
        ctx = context;
        mOnShowListener = onShowListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_show, parent, false);
        return new ViewHolder(view, mOnShowListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitle.setText(holder.mItem.getName());
        float averageRating = holder.mItem.getRating() != null ?
                holder.mItem.getRating().getAverage() : 0;
        holder.rbAverage.setRating(averageRating);
        holder.tvGenres.setText(holder.mItem.getGenres().toString());
        if (holder.mItem.getImage() != null) {
            Glide.with(ctx)
                    .load(holder.mItem.getImage().getMedium())
                    .into(holder.ivPoster);
        }
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    public void setShowList(List<ShowEntity> showList) {
        mValues = showList;
        notifyDataSetChanged();
    }

    public List<ShowEntity> getCurrentList() {
        return mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView tvTitle;
        public final ImageView ivPoster;
        public final RatingBar rbAverage;
        public final TextView tvGenres;
        public ShowEntity mItem;
        OnShowListener onShowListener;

        public ViewHolder(View view, OnShowListener onShowListener) {
            super(view);
            mView = view;
            tvTitle = view.findViewById(R.id.tvShowTitle);
            ivPoster = view.findViewById(R.id.ivShowPoster);
            rbAverage = view.findViewById(R.id.rbAverage);
            tvGenres = view.findViewById(R.id.tvShowGenre);
            this.onShowListener = onShowListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onShowListener.onShowClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnShowListener {
        void onShowClick(int position);
    }
}