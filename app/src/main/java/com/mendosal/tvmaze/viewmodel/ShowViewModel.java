package com.mendosal.tvmaze.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mendosal.tvmaze.data.ShowRepository;
import com.mendosal.tvmaze.data.network.Resource;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

public class ShowViewModel extends ViewModel {
    private final MutableLiveData<List<ShowEntity>> showsList;
    private ShowRepository showRepository;

    public ShowViewModel() {
        showRepository = new ShowRepository();
        showsList = showRepository.getShowEntities();
    }

    public LiveData<List<ShowEntity>> getShows() {
        return showsList;
    }
}
