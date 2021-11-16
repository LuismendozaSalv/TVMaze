package com.mendosal.tvmaze.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mendosal.tvmaze.repository.local.dao.ShowDao;
import com.mendosal.tvmaze.repository.local.models.Converters;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

@Database(entities = {ShowEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ShowRoomDatabase extends RoomDatabase {

    public abstract ShowDao getShowDao();
}
