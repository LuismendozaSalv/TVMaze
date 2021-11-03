package com.mendosal.tvmaze.presentation.ui.episodedetail;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;

public class EpisodeDetailFragment extends Fragment {
    private EpisodeEntity episode;
    private EpisodeDetailViewModel episodeDetailViewModel;
    View view;
    //Widgets
    private TextView tvSeasonEpisode;
    private TextView tvSummaryEpisode;
    private TextView tvEpisodeTitle;
    private ImageView ivEpisodePhoto;
    public EpisodeDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        episodeDetailViewModel =
                new ViewModelProvider(requireActivity()).get(EpisodeDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_episode_detail, container, false);
        initializeWidgets(view);
        return view;
    }

    private void initializeWidgets(View view) {
        tvSeasonEpisode = view.findViewById(R.id.tvSeasonEpisode);
        tvSummaryEpisode = view.findViewById(R.id.tvSummaryEpisode);
        ivEpisodePhoto = view.findViewById(R.id.ivEpisodePhoto);
        tvEpisodeTitle = view.findViewById(R.id.tvEpisodeTitle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EpisodeDetailFragmentArgs args = EpisodeDetailFragmentArgs.fromBundle(getArguments());
        int showId = args.getShowId();;
        int seasonNumber = args.getSeasonNumber();
        int episodeNumber = args.getEpisodeNumber();
        loadEpisodeInfo(showId, seasonNumber, episodeNumber);
    }

    private void loadEpisodeInfo(int showId, int seasonNumber, int episodeNumber) {
        episodeDetailViewModel.getEpisode(showId, seasonNumber, episodeNumber)
                .observe(requireActivity(), episodeEntity -> {
                    episode = episodeEntity;
                    selectedEpisodeInfo();
                });
    }

    private void selectedEpisodeInfo() {
        ivEpisodePhoto.setImageResource(R.drawable.no_image_available);
        if (episode.getImage() != null) {
            Glide.with(requireActivity())
                    .load(episode.getImage().getMedium())
                    .into(ivEpisodePhoto);
        }
        String seasonEpisode = "Season " + episode.getSeason() + ", Episode " + episode.getNumber();
        tvSeasonEpisode.setText(seasonEpisode);
        if (episode.getSummary() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvSummaryEpisode.setText(Html.fromHtml(episode.getSummary(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvSummaryEpisode.setText(Html.fromHtml(episode.getSummary()));
            }
        }else {
            tvSummaryEpisode.setText("-");
        }
        tvEpisodeTitle.setText(episode.getName());
    }
}