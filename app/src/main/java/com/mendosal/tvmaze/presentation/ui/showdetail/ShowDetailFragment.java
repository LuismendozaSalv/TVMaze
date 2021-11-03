package com.mendosal.tvmaze.presentation.ui.showdetail;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mendosal.tvmaze.R;
import com.mendosal.tvmaze.presentation.ui.episodedetail.EpisodeRecyclerViewAdapter;
import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;
import com.mendosal.tvmaze.retrofit.models.show.DayTime;
import com.mendosal.tvmaze.retrofit.models.show.Schedule;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ShowDetailFragment extends Fragment implements
        EpisodeRecyclerViewAdapter.OnEpisodeListener, AdapterView.OnItemSelectedListener {
    private ShowEntity show;
    private List<EpisodeEntity> episodesList;
    Map<Integer, List<EpisodeEntity>> episodesPerSeason;
    private ShowDetailViewModel showDetailViewModel;
    private List<Integer> seasonsKeys;
    ShowDetailFragmentArgs args;
    View view;
    //widgets
    private TextView tvRating;
    private TextView tvStatus;
    private TextView tvNetwork;
    private TextView tvSummary;
    private ImageView ivPoster;
    private RecyclerView rvGenres;
    private RecyclerView rvSchedule;
    private RecyclerView rvEpisodes;
    private Spinner spinSeasons;

    public ShowDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDetailViewModel = new
                ViewModelProvider(requireActivity()).get(ShowDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_detail, container, false);
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
        rvEpisodes = view.findViewById(R.id.rvEpisodes);
        spinSeasons = view.findViewById(R.id.spinSeasons);
        rvSchedule = view.findViewById(R.id.rvSchedule);
        spinSeasons.setOnItemSelectedListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = ShowDetailFragmentArgs.fromBundle(getArguments());
        int showId = args.getShowId();;
        loadShowInfo(showId);
    }

    private void loadShowInfo(int showId) {
        showDetailViewModel.getShow(showId).observe(requireActivity(), showEntity -> {
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(show.getName());
        loadShowGenres(show.getGenres());
        loadShowEpisodes((show.getId()));
        loadSchedule(show.getSchedule());
    }

    private void loadShowGenres(List<String> genreList) {
        rvGenres.setLayoutManager(new GridLayoutManager(getContext(), 3));
        GenreRecyclerViewAdapter adapter = new GenreRecyclerViewAdapter(genreList);
        rvGenres.setAdapter(adapter);
    }

    private void loadSchedule(Schedule schedule) {
        List<DayTime> dayTimeList = showDetailViewModel.getDayTimeList(schedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(requireActivity()));
        ScheduleRecyclerViewAdapter adapter = new ScheduleRecyclerViewAdapter(dayTimeList);
        rvSchedule.setAdapter(adapter);
    }

    private void loadShowEpisodes(int showId) {
        showDetailViewModel.getShowEpisodes(showId).observe(requireActivity(), new Observer<List<EpisodeEntity>>() {
            @Override
            public void onChanged(List<EpisodeEntity> episodeEntities) {
                episodesList = episodeEntities;
                if (episodesList != null) {
                    episodesPerSeason =
                            showDetailViewModel.getEpisodesPerSeason(episodesList);
                    loadSeasonsSpinner();
                }
            }
        });
    }

    private void loadSeasonsSpinner() {
        List<String> seasons = new ArrayList<>();
        Set<Integer> keys = episodesPerSeason.keySet();
        seasonsKeys = new ArrayList<>();
        seasonsKeys.addAll(keys);
        for (Integer k : keys) {
            seasons.add("Season " + k);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                seasons);
        spinSeasons.setAdapter(adapter);

    }

    private void setShowEpisodes(List<EpisodeEntity> episodeEntities) {
        rvEpisodes.setLayoutManager(new LinearLayoutManager(requireActivity()));
        EpisodeRecyclerViewAdapter adapter =
                new EpisodeRecyclerViewAdapter(requireActivity(), episodeEntities, this);
        rvEpisodes.setAdapter(adapter);
    }

    @Override
    public void onEpisodeClick(int position) {
        int selectedSeason = spinSeasons.getSelectedItemPosition();
        Integer selectedKey = seasonsKeys.get(selectedSeason);
        List<EpisodeEntity> selectedEpisodes = episodesPerSeason.get(selectedKey);
        ShowDetailFragmentDirections.ActionShowDetailFragmentToEpisodeDetailFragment action =
                ShowDetailFragmentDirections.actionShowDetailFragmentToEpisodeDetailFragment();
        EpisodeEntity selectedEpisode = selectedEpisodes.get(position);
        action.setShowId(show.getId());
        action.setSeasonNumber(selectedEpisode.getSeason());
        action.setEpisodeNumber(selectedEpisode.getNumber());
        Navigation.findNavController(view).navigate(action);
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        Integer selectedKey = seasonsKeys.get(pos);
        setShowEpisodes(episodesPerSeason.get(selectedKey));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}