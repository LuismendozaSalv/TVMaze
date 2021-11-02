package com.mendosal.tvmaze.ui;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

public class EpisodeRecyclerViewAdapter extends RecyclerView.Adapter<EpisodeRecyclerViewAdapter.ViewHolder> {

    private List<EpisodeEntity> mValues;
    Context ctx;
    private OnEpisodeListener mOnEpisodeListener;

    public EpisodeRecyclerViewAdapter(Context context, List<EpisodeEntity> items, OnEpisodeListener onEpisodeListener) {
        mValues = items;
        ctx = context;
        mOnEpisodeListener = onEpisodeListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_item, parent, false);
        return new ViewHolder(view, mOnEpisodeListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvName.setText(holder.mItem.getName());
        holder.tvDuration.setText(holder.mItem.getAirdate());
        int runtime = holder.mItem.getRuntime() != null ? holder.mItem.getRuntime() : 0;
        String runtimeString = runtime > 0 ? runtime + "m" : "-";
        holder.tvSummary.setText(runtimeString);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvSummary.setText(Html.fromHtml(holder.mItem.getSummary(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvSummary.setText(Html.fromHtml(holder.mItem.getSummary()));
        }
        if (holder.mItem.getImage() != null) {
            Glide.with(ctx)
                    .load(holder.mItem.getImage().getMedium())
                    .into(holder.ivPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    public void setEpisodesList(List<EpisodeEntity> episodesList) {
        mValues = episodesList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView tvName;
        public final ImageView ivPhoto;
        public final TextView tvDuration;
        public final TextView tvSummary;
        public EpisodeEntity mItem;
        OnEpisodeListener onEpisodeListener;

        public ViewHolder(View view, OnEpisodeListener onEpisodeListener) {
            super(view);
            mView = view;
            tvName = view.findViewById(R.id.tvEpisodeName);
            tvDuration = view.findViewById(R.id.tvEpisodeDuration);
            tvSummary = view.findViewById(R.id.tvEpisodeSummary);
            ivPhoto = view.findViewById(R.id.ivEpisodePhoto);
            this.onEpisodeListener = onEpisodeListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onEpisodeListener.onEpisodeClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnEpisodeListener {
        void onEpisodeClick(int position);
    }
}