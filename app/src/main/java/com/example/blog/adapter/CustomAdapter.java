package com.example.blog.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blog.R;
import com.example.blog.UpdateActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private ArrayList id = null;
    private ArrayList basic_title;
    private ArrayList basic_anchor;
    private ArrayList basic_pages;
    Activity activity;
    Animation tranlste_anim;


    public CustomAdapter(Activity activity,
                         Context context,
                         ArrayList id,
                         ArrayList basic_title,
                         ArrayList basic_anchor,
                         ArrayList basic_pages) {

        this.activity = activity;
        this.context = context;
        this.id = id;
        this.basic_title = basic_title;
        this.basic_anchor = basic_anchor;
        this.basic_pages = basic_pages;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.custom_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.basic_id_txt.setText(String.valueOf(id.get(position)));
        holder.basic_title_txt.setText(String.valueOf(basic_title.get(position)));
        holder.basic_anchor_txt.setText(String.valueOf(basic_anchor.get(position)));
        holder.basic_pages_txt.setText(String.valueOf(basic_pages.get(position)));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("title", String.valueOf(basic_title.get(position)));
                intent.putExtra("anchor", String.valueOf(basic_anchor.get(position)));
                intent.putExtra("pages", String.valueOf(basic_pages.get(position)));
                activity.startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (id != null){
            return id.size();
        }
        return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView basic_id_txt, basic_title_txt, basic_anchor_txt, basic_pages_txt;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            basic_id_txt = itemView.findViewById(R.id.basicId);
            basic_title_txt = itemView.findViewById(R.id.basicTitle);
            basic_anchor_txt = itemView.findViewById(R.id.basicAnchor);
            basic_pages_txt = itemView.findViewById(R.id.basicPages);
            constraintLayout = itemView.findViewById(R.id.custom_layout);
            tranlste_anim = AnimationUtils.loadAnimation(context, R.anim.anim);
            constraintLayout.setAnimation(tranlste_anim);
        }
    }
}
