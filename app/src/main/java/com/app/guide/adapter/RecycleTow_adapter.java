package com.app.guide.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.guide.OnItemClickListener;
import com.app.guide.R;
import com.app.guide.model.Second_model;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class RecycleTow_adapter extends RecyclerView.Adapter<RecycleTow_adapter.ViewHolder>  {
    private List<Second_model> details;
    private Context context;

    private List<Second_model> dataList;

    public RecycleTow_adapter(List<Second_model> details, Context context) {
        this.details = details;
        this.context = context;
    }


    public void updateData(List<Second_model> newData) {

        this.details = newData;
        notifyDataSetChanged();

    }


    private OnItemClickListener listener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titles;
        public TextView description;
        public ConstraintLayout images;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titles= itemView.findViewById(R.id.titles);
            images=itemView.findViewById(R.id.contraint_image);
            description =itemView.findViewById(R.id.description);

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
    public RecycleTow_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View DestailsView = inflater.inflate(R.layout.second_item, parent, false);

        // Return a new holder instance
        RecycleTow_adapter.ViewHolder viewHolder = new RecycleTow_adapter.ViewHolder(DestailsView);
        return viewHolder;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(RecycleTow_adapter.ViewHolder holder, int position) {


        Second_model Details = details.get(position);
        // Set item views based on your views and data model
        TextView title = holder.titles;
        title.setText(Details.getTitles());
        TextView desc = holder.description;
        desc.setText(Details.getDescription());
        ConstraintLayout image = holder.images;
        Glide.with(holder.itemView.getContext())
                .load(Details.getImages())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        image.setBackground(resource);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }


    @Override
    public int getItemCount() {
        return  details.size();
    }
}
