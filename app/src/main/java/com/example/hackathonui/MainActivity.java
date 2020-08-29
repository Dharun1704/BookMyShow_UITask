package com.example.hackathonui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private ViewPager viewPager;
    private HomeScreenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setClipToPadding(false);

        setMoviesArray();
        adapter = new HomeScreenAdapter(this, movies);
        viewPager.setPageTransformer(true, new VerticalPageTransformer());
        viewPager.setAdapter(adapter);


    }

    private void setMoviesArray() {
        movies = new ArrayList<>();

        Movie movie = new Movie();
        movie.setImage(R.drawable.image2);
        movie.setName("Blade Runner 2049");
        movie.setImdb("IMDB: 8.0");
        movie.setGenre("Action, Drama, Mystery");
        movie.setDesc("K, an officer with the Los Angeles Police Department, " +
                "unearths a secret that could cause chaos. He goes in search of a " +
                "former blade runner who has been missing for three decades.");
        movies.add(movie);

        movie = new Movie();
        movie.setImage(R.drawable.image1);
        movie.setName("Avengers: Endgame");
        movie.setImdb("IMDB: 8.4");
        movie.setGenre("Action, Drama, Adventure");
        movie.setDesc("After the devastating events of Avengers: Infinity War, the universe is in ruins. " +
                "With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' " +
                "actions and restore balance to the universe.");
        movies.add(movie);

        movie = new Movie();
        movie.setImage(R.drawable.image3);
        movie.setName("Alien: Covenant");
        movie.setImdb("IMDB: 6.4");
        movie.setGenre("Horror, Scifi, Thriller");
        movie.setDesc("The crew of a colony ship, bound for a remote planet, discover an uncharted paradise with" +
                " a threat beyond their imagination, and must attempt a harrowing escape.");
        movies.add(movie);
    }

    private static class VerticalPageTransformer implements ViewPager.PageTransformer{

        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position >= 0) {
                page.setScaleX(0.8f - 0.05f*position);
                page.setScaleY(0.8f);

                page.setTranslationX(-page.getWidth()*position);
                page.setTranslationY(-10*position);
            }
        }
    }
}