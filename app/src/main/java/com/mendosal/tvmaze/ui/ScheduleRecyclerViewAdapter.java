package com.mendosal.tvmaze.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.retrofit.models.show.DayTime;

import java.util.List;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter<ScheduleRecyclerViewAdapter.ViewHolder> {

    private List<DayTime> mValues;

    public ScheduleRecyclerViewAdapter(List<DayTime> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvDay.setText(holder.mItem.getDay());
        holder.tvTime.setText(holder.mItem.getTime());
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final TextView tvDay;
        public final TextView tvTime;
        public DayTime mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvDay = view.findViewById(R.id.tvScheduleDay);
            tvTime = view.findViewById(R.id.tvScheduleTime);
        }


    }
}