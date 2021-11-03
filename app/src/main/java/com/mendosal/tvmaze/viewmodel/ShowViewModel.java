package com.mendosal.tvmaze.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mendosal.tvmaze.data.ShowRepository;
import com.mendosal.tvmaze.retrofit.models.show.ScoreShow;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShowViewModel extends ViewModel {
    private MutableLiveData<List<ShowEntity>> showsList;
    private MutableLiveData<List<ScoreShow>> searchShowsList;
    private ShowRepository showRepository;

    @Inject
    public ShowViewModel(ShowRepository showRepositoryI) {
        showRepository = showRepositoryI;
    }

    public LiveData<List<ShowEntity>> getShows(int page) {
        showsList = showRepository.getShowEntities(page);
        return showsList;
    }

    public LiveData<List<ScoreShow>> searchShow(String q) {
        searchShowsList = showRepository.searchShow(q);
        return searchShowsList;
    }

    public List<ShowEntity> getShowsListFromSearch(
            List<ScoreShow> searchShowsList) {
        List<ShowEntity> showEntityList = new ArrayList<>();
        if (searchShowsList != null) {
            for (ScoreShow scoreShow :
                    searchShowsList) {
                showEntityList.add(scoreShow.getShow());
            }
        }
        return showEntityList;
    }
}
