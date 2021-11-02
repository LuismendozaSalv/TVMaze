package com.mendosal.tvmaze.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mendosal.tvmaze.data.ShowRepository;
import com.mendosal.tvmaze.data.network.Resource;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShowViewModel extends ViewModel {
    private MutableLiveData<List<ShowEntity>> showsList;
    private ShowRepository showRepository;

    @Inject
    public ShowViewModel(ShowRepository showRepositoryI) {
        showRepository = showRepositoryI;
    }

    public LiveData<List<ShowEntity>> getShows(int page) {
        showsList = showRepository.getShowEntities(page);
        return showsList;
    }
}
