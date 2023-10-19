package com.app.guide.adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.app.guide.OnItemClickListener;
import com.app.guide.R;
import com.app.guide.model.First_model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;


public class Rcycleone_Adapter extends RecyclerView.Adapter<Rcycleone_Adapter.ViewHolder> {

    private List<First_model> details;

    public Rcycleone_Adapter(List<First_model> details) {
        this.details = details;
    }


    private OnItemClickListener listener;


    public void updateData(List<First_model> newData) {

        this.details = newData;
        notifyDataSetChanged();

    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView title;
        public TextView desc;
        public ImageView image;

        public ConstraintLayout backrgound_image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titled);
            desc = itemView.findViewById(R.id.desc);
            image = itemView.findViewById(R.id.image_first);
            backrgound_image = itemView.findViewById(R.id.backrgound_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View DestailsView = inflater.inflate(R.layout.first_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(DestailsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        First_model Details = details.get(position);
        // Set item views based on your views and data model
        TextView title = holder.title;
        title.setText(Details.getTitle());
        TextView desc = holder.desc;
        desc.setText(Details.getDescription());
        ImageView image = holder.image;
        Glide.with(holder.itemView.getContext())
                .load(Details.getImage())
                .into(image);
        ConstraintLayout image_background = holder.backrgound_image;
        Glide.with(holder.itemView.getContext())
                .load(Details.getImagebackground())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        image_background.setBackground(resource);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
   }

