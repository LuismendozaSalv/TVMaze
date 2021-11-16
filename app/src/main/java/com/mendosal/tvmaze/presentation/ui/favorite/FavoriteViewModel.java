package com.mendosal.tvmaze.presentation.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mendosal.tvmaze.repository.ShowRepository;
import com.mendosal.tvmaze.retrofit.models.show.ScoreShow;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;
import com.mendosal.tvmaze.retrofit.network.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<List<ShowEntity>> showsList;
    private MutableLiveData<List<ScoreShow>> searchShowsList;
    private ShowRepository showRepository;

    @Inject
    public FavoriteViewModel(ShowRepository showRepositoryI) {
        showRepository = showRepositoryI;
    }

    public LiveData<Resource<ShowEntity>> saveFavoriteShow(int showId) {
        return showRepository.saveFavoriteShow(showId);
    }

    public LiveData<Resource<List<ShowEntity>>> getFavoriteShows() {
        return showRepository.getFavoritesShows();
    }
}
