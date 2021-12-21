package com.example.notes_app_room_database.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notes_app_room_database.Models.Notess;

@Database(entities = Notess.class, version = 1, exportSchema = false)
public abstract class Room_DB extends RoomDatabase {
    private static Room_DB room_db;
    private static String DATABASE_NAMe = "NoteApp";

    public synchronized static Room_DB getInstance(Context context) {
        if (room_db == null) {
            room_db = Room.databaseBuilder(context.getApplicationContext(), Room_DB.class,
                    DATABASE_NAMe).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build();
        }
        return room_db;

    }

    //main dao
    public abstract  Main_dao main_dao();
}

