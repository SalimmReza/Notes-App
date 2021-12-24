package com.example.notes_app_room_database;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.RoomDatabase;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notes_app_room_database.Adapters.Notes_list_Adapter;
import com.example.notes_app_room_database.Database.Room_DB;
import com.example.notes_app_room_database.Models.Notess;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  PopupMenu.OnMenuItemClickListener{

    RecyclerView recyclerView;
    Notes_list_Adapter notes_list_adapter;
    List<Notess> notes = new ArrayList<>();
    Room_DB database;
    FloatingActionButton fabb_add;
    SearchView search_view_home;

    Notess selected_note;

    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home_id);
        fabb_add = findViewById(R.id.fab_add_id);
        search_view_home= findViewById(R.id.search_view_home_id);
        swipeRefreshLayout=findViewById(R.id.swipe_layout_id);

        database = Room_DB.getInstance(this);
        notes = database.main_dao().get_all();

        update_recycler(notes);

        fabb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NotesTaker_Activity.class);
                startActivityForResult(intent, 101);

            }
        });


      /*  search_view_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }*/
          search_view_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });


          swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
              @Override
              public void onRefresh() {

                  swipeRefreshLayout.setRefreshing(false);
                  notes_list_adapter.notifyDataSetChanged();
              }
          });
    }

    private void filter(String newText) {
        List<Notess> filtered_list = new ArrayList<>();
        for (Notess single_notes : notes)
        {
            if (single_notes.getTitle().toLowerCase().contains(newText.toLowerCase())
            || single_notes.getNotes().toLowerCase().contains(newText.toLowerCase()))
            {
                filtered_list.add(single_notes);
            }
        }
        notes_list_adapter.filter_list(filtered_list);
    }

  /*  private void filter(String newText) {
        List<Notess> filtered_list = new ArrayList<>();
        for (Notess single_notes : notes)
        {
            if (single_notes.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || single_notes.getNotes().toLowerCase().contains(newText.toLowerCase()))
            {
                filtered_list.add(single_notes);
            }
        }
        notes_list_adapter.filter_list(filtered_list);
    }*/


    //work id for calling the notes taker activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== 101)
        {
            if (resultCode == Activity.RESULT_OK)
            {
               Notess new_notes = (Notess) data.getSerializableExtra("notes");
               database.main_dao().insert(new_notes);
               notes.clear();
               notes.addAll(database.main_dao().get_all());
               notes_list_adapter.notifyDataSetChanged();
            }
        }


         else if(requestCode==102)
        {
            if (resultCode==Activity.RESULT_OK)
            {
                Notess new_notes= (Notess) data.getSerializableExtra("notes");
                database.main_dao().update(new_notes.getID(), new_notes.getTitle(), new_notes.getNotes());
                notes.clear();
                notes.addAll(database.main_dao().get_all());
                notes_list_adapter.notifyDataSetChanged();
            }
        }
    }

    private void update_recycler(List<Notess> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notes_list_adapter = new Notes_list_Adapter(MainActivity.this, notes, notes_click_listener);
        recyclerView.setAdapter(notes_list_adapter);

    }

    private final Notes_click_listener notes_click_listener = new Notes_click_listener() {
        @Override
        public void Onclick(Notess notess) {


             Intent intent = new Intent(MainActivity.this, NotesTaker_Activity.class);
            intent.putExtra("old_notes", notess);
            startActivityForResult(intent, 102);
        }

        @Override
        public void Onlongclick(Notess notess, CardView cardView) {
            selected_note = new Notess();
            selected_note = notess;
            show_pop_up(cardView);

        }
    };

    private void show_pop_up(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.pin_id:
                if (selected_note.isPin())
                {
                    database.main_dao().pin(selected_note.getID(), false);
                    Toast.makeText(MainActivity.this, "UnPinned", Toast.LENGTH_SHORT).show();
                }else
                {
                    database.main_dao().pin(selected_note.getID(), true);
                    Toast.makeText(MainActivity.this, "Pinned", Toast.LENGTH_SHORT).show();

                }
                notes.clear();
                notes.addAll(database.main_dao().get_all());
                notes_list_adapter.notifyDataSetChanged();
                return true;

            case R.id.delete_id:
                database.main_dao().delete(selected_note);
                notes.remove(selected_note);
                notes_list_adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;

        }


    }
}