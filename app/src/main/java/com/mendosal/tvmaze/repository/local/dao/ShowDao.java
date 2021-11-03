package com.mendosal.tvmaze.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

@Dao
public interface ShowDao {

    @Query("SELECT * FROM shows")
    LiveData<List<ShowEntity>> loadFavoriteShows();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavoriteShow(ShowEntity show);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavoriteShows(List<ShowEntity> show);
}
