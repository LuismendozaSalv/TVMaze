package com.mendosal.tvmaze.data.local;

import android.widget.TableRow;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mendosal.tvmaze.data.local.dao.ShowDao;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

@Database(entities = {ShowEntity.class}, version = 1, exportSchema = false)
public abstract class ShowRoomDatabase extends RoomDatabase {

    public abstract ShowDao getShowDao();
}
