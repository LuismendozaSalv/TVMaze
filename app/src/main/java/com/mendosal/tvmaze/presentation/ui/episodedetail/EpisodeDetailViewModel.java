package com.mendosal.tvmaze.presentation.ui.episodedetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mendosal.tvmaze.repository.EpisodeRepository;
import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;

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
