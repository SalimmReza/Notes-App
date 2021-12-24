package com.example.notes_app_room_database.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes_app_room_database.Models.Notess;
import com.example.notes_app_room_database.Notes_click_listener;
import com.example.notes_app_room_database.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Notes_list_Adapter extends RecyclerView.Adapter<notes_view_holder>{
    Context context;
    List<Notess> list;
    Notes_click_listener listener;

    public Notes_list_Adapter(Context context, List<Notess> list, Notes_click_listener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public notes_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notes_view_holder(LayoutInflater.from(context).inflate(R.layout.note_list, parent , false));

    }

    @Override
    public void onBindViewHolder(@NonNull notes_view_holder holder, int position) {

        holder.tv_title.setText(list.get(position).getTitle());
      //horizontal scrolling effect
        holder.tv_title.setSelected(true);

        holder.tv_notes.setText(list.get(position).getNotes());

        holder.tv_date.setText(list.get(position).getDate());
        holder.tv_date.setSelected(true);

        if (list.get(position).isPin())
        {
            holder.pin_iv.setImageResource(R.drawable.pin);
        } else
        {
            holder.pin_iv.setImageResource(0);
        }

        int colorr_code = get_random_color();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(colorr_code));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.Onclick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.Onlongclick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });
    }

    //colors

    private int get_random_color()
    {
        List<Integer> color_code = new ArrayList<>();
        color_code.add(R.color.blue);
        color_code.add(R.color.green);
        color_code.add(R.color.yellow);
        color_code.add(R.color.red);
        color_code.add(R.color.violet);
        color_code.add(R.color.orange);

        Random random = new Random();
        int random_color = random.nextInt(color_code.size());
        return color_code.get(random_color);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public void filter_list(List<Notess> filtered_list)
    {
        list= filtered_list;
        notifyDataSetChanged();
    }



  /*  public void filter_list(List<Notess> filtered_list)
    {
        list= filtered_list;
        notifyDataSetChanged();
    }*/
}


class notes_view_holder extends RecyclerView.ViewHolder {


CardView notes_container;
TextView tv_title, tv_notes , tv_date;
ImageView pin_iv;

    public notes_view_holder(@NonNull View itemView) {
        super(itemView);

        notes_container = itemView.findViewById(R.id.notes_container_id);
        tv_title = itemView.findViewById(R.id.text_view_title_id);
        tv_notes = itemView.findViewById(R.id.notes_id);
        tv_date = itemView.findViewById(R.id.dates_id);
        pin_iv = itemView.findViewById(R.id.pin_id);

    }
}
