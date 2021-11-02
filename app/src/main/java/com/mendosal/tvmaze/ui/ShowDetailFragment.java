package com.mendosal.tvmaze.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;
import com.mendosal.tvmaze.viewmodel.ShowDetailViewModel;

import java.util.List;


public class ShowDetailFragment extends Fragment {
    private ShowEntity show;
    private ShowDetailViewModel showDetailViewModel;
    ShowDetailFragmentArgs args;
    public ShowDetailFragment() { }
    //widgets
    private TextView tvRating;
    private TextView tvStatus;
    private TextView tvNetwork;
    private TextView tvSummary;
    private ImageView ivPoster;
    private RecyclerView rvGenres;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDetailViewModel = new
                ViewModelProvider(requireActivity()).get(ShowDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_detail, container, false);
        initializeWidgets(view);
        return view;
    }

    private void initializeWidgets(View view) {
        tvRating = view.findViewById(R.id.tvRating);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvSummary = view.findViewById(R.id.tvSummary);
        tvNetwork = view.findViewById(R.id.tvNetwork);
        ivPoster = view.findViewById(R.id.ivPoster);
        rvGenres = view.findViewById(R.id.rvGenres);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = ShowDetailFragmentArgs.fromBundle(getArguments());
        int showId = args.getShowId();;
        loadShowInfo(showId);
    }

    private void loadShowInfo(int showId) {
        showDetailViewModel.getShow(showId).observe(getActivity(), showEntity -> {
            show = showEntity;
            selectedShowInfo();
        });
    }

    private void selectedShowInfo() {
        String ratingAverage = show.getRating() != null ? String.valueOf(show.getRating().getAverage()) : "-";
        tvRating.setText(ratingAverage);
        tvStatus.setText(String.valueOf(show.getStatus()));
        String netWorkName = show.getNetwork() != null ? show.getNetwork().getName() : "-";
        tvNetwork.setText(netWorkName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvSummary.setText(Html.fromHtml(show.getSummary(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvSummary.setText(Html.fromHtml(show.getSummary()));
        }
        Glide.with(requireActivity())
                .load(show.getImage().getOriginal())
                .into(ivPoster);
        loadShowGenres(show.getGenres());
    }

    private void loadShowGenres(List<String> genreList) {
        rvGenres.setLayoutManager(new GridLayoutManager(getContext(), 3));
        GenreRecyclerViewAdapter adapter = new GenreRecyclerViewAdapter(genreList);
        rvGenres.setAdapter(adapter);
    }
}