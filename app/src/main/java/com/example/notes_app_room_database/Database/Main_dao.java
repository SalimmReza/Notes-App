package com.example.notes_app_room_database.Database;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notes_app_room_database.Models.Notess;

import java.util.List;

@Dao //DATA ACCESS OBJECT
public interface Main_dao {

    @Insert(onConflict = REPLACE)
    void insert(Notess notess);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notess> get_all();

    @Query("UPDATE notes SET title = :title, notes= :notes WHERE ID = :id")
    void update(int id , String title, String notes);

    @Delete
    void delete(Notess notess);



}
