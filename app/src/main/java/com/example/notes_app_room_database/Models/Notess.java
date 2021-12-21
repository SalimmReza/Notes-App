package com.example.notes_app_room_database.Models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//serializable because when we try to send data from one activity to another activity

@Entity(tableName = "notes")
public class Notess implements Serializable {
    @PrimaryKey(autoGenerate = true)//it will generate an id all the time when we input new items
    int ID = 0;

    @ColumnInfo(name = "title")
    String title= "";

    @ColumnInfo(name = "notes")
    String notes ="";

    @ColumnInfo(name= "date")
    String date ="";

    @ColumnInfo(name= "pinned")
    boolean pin= false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPin() {
        return pin;
    }

    public void setPin(boolean pin) {
        this.pin = pin;
    }
}
