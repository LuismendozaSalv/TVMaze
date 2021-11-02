package com.mendosal.tvmaze.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mendosal.tvmaze.data.EpisodeRepository;
import com.mendosal.tvmaze.data.ShowRepository;
import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EpisodeDetailViewModel extends ViewModel {
    private MutableLiveData<EpisodeEntity> selectedEpisode;
    private EpisodeRepository episodeRepository;

    @Inject
    public EpisodeDetailViewModel(EpisodeRepository episodeRepositoryI) {
        episodeRepository = episodeRepositoryI;
    }

    public LiveData<EpisodeEntity> getEpisode(int showId, int seasonNumber, int episodeNumber) {
        selectedEpisode = episodeRepository.getShowEpisode(showId, seasonNumber, episodeNumber);
        return selectedEpisode;
    }
}
