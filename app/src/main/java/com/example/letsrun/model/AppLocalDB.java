package com.example.letsrun.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.letsrun.MyApplication;

@Database(entities = {User.class , RunningTracks.class}, version = 7)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao UserDao();
    public abstract RunningTracksDao RunningTracksDao();
}

public class AppLocalDB {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(MyApplication.context,
                    AppLocalDbRepository.class,
                    "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
}
