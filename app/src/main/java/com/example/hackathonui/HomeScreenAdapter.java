package com.example.hackathonui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class HomeScreenAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Movie> movies;

    public HomeScreenAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (CardView) object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        TextView name1, name2, imdb, desc, genre;
        final ImageView image;
        ImageButton bottomBar;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_item, container, false);
        container.addView(view);

        image = view.findViewById(R.id.slider_image);
        name1 = view.findViewById(R.id.detail_name1);
        name2 = view.findViewById(R.id.detail_name2);
        imdb = view.findViewById(R.id.detail_imdb);
        desc = view.findViewById(R.id.detail_desc);
        genre = view.findViewById(R.id.detail_genre);
        bottomBar = view.findViewById(R.id.bottomBar);

        final Movie movie = movies.get(position);
        String[] name = movie.getName().split(" ");
        String remName = "";
        name1.setText(name[0]);
        if (name.length > 1) {
            for (int i = 1; i < name.length; i++) {
                remName += name[i] + " ";
            }
            name2.setText(remName);
        }
        else
            name2.setVisibility(View.INVISIBLE);
        image.setImageResource(movie.getImage());
        imdb.setText(movie.getImdb());
        desc.setText(movie.getDesc());
        genre.setText(movie.getGenre());

        bottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("name", movie.getName());
                i.putExtra("imdb", movie.getImdb());
                i.putExtra("genre", movie.getGenre());
                i.putExtra("desc", movie.getDesc());
                Pair<View, String> pair = Pair.create((View) image, "image");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) context,
                        pair
                );
                context.startActivity(i, optionsCompat.toBundle());
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
