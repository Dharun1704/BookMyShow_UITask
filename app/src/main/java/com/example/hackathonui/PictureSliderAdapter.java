package com.example.hackathonui;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PictureSliderAdapter extends RecyclerView.Adapter<PictureSliderAdapter.ViewHolder> {

    private Context context;

    public PictureSliderAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.pic_slider, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            holder.image.setImageResource(R.drawable.image2);
            holder.frameLayout.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
        }
        if (position == 1) {
            holder.image.setVisibility(View.GONE);
            holder.frameLayout.setVisibility(View.VISIBLE);
            String videoPath = "android.resource://" + context.getPackageName() + "/" + R.raw.video1;
            Uri uri = Uri.parse(videoPath);
            holder.video.setVideoURI(uri);

            MediaController mediaController = new MediaController(context);
            holder.video.setMediaController(mediaController);
            mediaController.setAnchorView(holder.video);
        }
        if (position == 2) {
            holder.frameLayout.setVisibility(View.GONE);
            holder.image.setVisibility(View.VISIBLE);
            holder.image.setImageResource(R.drawable.image21);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        VideoView video;
        FrameLayout frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.detail_image);
            video = itemView.findViewById(R.id.videoView);
            frameLayout = itemView.findViewById(R.id.frameLayout);
        }
    }
}
