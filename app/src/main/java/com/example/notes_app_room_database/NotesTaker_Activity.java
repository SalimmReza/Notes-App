package com.example.notes_app_room_database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notes_app_room_database.Models.Notess;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTaker_Activity extends AppCompatActivity {

    private EditText n_title, n_notes;
  private  ImageView n_save;
  Notess notess;

  boolean is_old_note = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);


        n_title= findViewById(R.id.edittext_title_id);
        n_notes= findViewById(R.id.edittext_notes_id);
        n_save= findViewById(R.id.image_view_save_id);




          notess = new Notess();
        try {
            notess = (Notess) getIntent().getSerializableExtra("old_notes");
            n_title.setText(notess.getTitle());
            n_notes.setText(notess.getNotes());
            is_old_note=true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        n_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = n_title.getText().toString();
                String description = n_notes.getText().toString();

                if (description.isEmpty())
                {
                    Toast.makeText(NotesTaker_Activity.this, "Description can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if (!is_old_note)
                {
                    notess= new Notess();

                }

                notess.setTitle(title);
                notess.setNotes(description);
                notess.setDate(format.format(date));

                //in java class if we do intent.put extra we have to make a class serializable
               //means if u are calling the model class as intent thn only
                Intent intent = new Intent();
                intent.putExtra("notes", notess);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
    }
}