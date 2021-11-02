package com.mendosal.tvmaze.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mendosal.tvmaze.data.ShowRepository;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShowDetailViewModel extends ViewModel {
    private MutableLiveData<ShowEntity> selectedShow;
    private ShowRepository showRepository;

    @Inject
    public ShowDetailViewModel(ShowRepository showRepositoryI) {
        showRepository = showRepositoryI;
    }

    public LiveData<ShowEntity> getShow(int showId) {
        selectedShow = showRepository.getShow(showId);
        return selectedShow;
    }
}
