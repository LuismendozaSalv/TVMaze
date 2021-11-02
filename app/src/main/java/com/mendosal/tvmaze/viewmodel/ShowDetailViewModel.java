package com.mendosal.tvmaze.viewmodel;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mendosal.tvmaze.data.EpisodeRepository;
import com.mendosal.tvmaze.data.ShowRepository;
import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;
import com.mendosal.tvmaze.retrofit.models.show.DayTime;
import com.mendosal.tvmaze.retrofit.models.show.Schedule;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShowDetailViewModel extends ViewModel {
    private MutableLiveData<ShowEntity> selectedShow;
    private MutableLiveData<List<EpisodeEntity>> showEpisodesList;
    private ShowRepository showRepository;
    private EpisodeRepository episodeRepository;

    @Inject
    public ShowDetailViewModel(ShowRepository showRepositoryI, EpisodeRepository episodeRepositoryI) {
        showRepository = showRepositoryI;
        episodeRepository = episodeRepositoryI;
    }

    public LiveData<ShowEntity> getShow(int showId) {
        selectedShow = showRepository.getShow(showId);
        return selectedShow;
    }

    public LiveData<List<EpisodeEntity>> getShowEpisodes(int showId) {
        showEpisodesList = episodeRepository.getShowEpisodes(showId);
        return showEpisodesList;
    }

    public Map<Integer, List<EpisodeEntity>> getEpisodesPerSeason(List<EpisodeEntity> episodeList) {
        Map<Integer, List<EpisodeEntity>> episodesPerSeason2 = new HashMap<>();
        for (EpisodeEntity episode: episodeList) {
            int season = episode.getSeason();
            if (!episodesPerSeason2.containsKey(season)) {
                episodesPerSeason2.put(season, new ArrayList<>());
            }
            episodesPerSeason2.get(season).add(episode);
        }
        return episodesPerSeason2;
    }

    public List<DayTime> getDayTimeList(Schedule schedule) {
        List<DayTime> dayTimeList = new ArrayList<>();
        for (String day :
             schedule.getDays()) {
            dayTimeList.add(new DayTime(day, schedule.getTime()));
        }
        return dayTimeList;
    }
}
