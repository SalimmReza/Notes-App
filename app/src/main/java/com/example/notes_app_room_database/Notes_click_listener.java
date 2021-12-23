package com.example.notes_app_room_database;

import androidx.cardview.widget.CardView;

import com.example.notes_app_room_database.Models.Notess;

public interface Notes_click_listener {
    void Onclick(Notess notess);
    void Onlongclick(Notess notess , CardView cardView);
}
