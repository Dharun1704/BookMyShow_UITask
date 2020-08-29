package com.example.hackathonui;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private PictureSliderAdapter adapter;

    private ViewPager2 viewPager2;
    private TextView name1, name2, imdb, desc, genre;
    private ImageButton[] buttons;
    private Button sessionTime, bookSeats, checkOut;
    private NestedScrollView details, session;
    private RelativeLayout[] layouts;
    private View[] views;
    private View viewPagerLine;
    private TextView[] texts;
    private CardView detailCard, seatCard;
    private Button[][] seatButtons;

    String timeSelected = "";

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        details = findViewById(R.id.details_nsv);
        session = findViewById(R.id.session_nsv);
        detailCard = findViewById(R.id.detailsCard);
        seatCard = findViewById(R.id.seatCard);

        viewPager2 = findViewById(R.id.detailViewPager);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.75f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        adapter = new PictureSliderAdapter(this);
        viewPager2.setAdapter(adapter);

        buttons = new ImageButton[3];
        buttons[0] = findViewById(R.id.img1);
        buttons[1] = findViewById(R.id.img2);
        buttons[2] = findViewById(R.id.img3);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    buttons[0].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg2));
                    buttons[1].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg1));
                    buttons[2].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg1));
                }
                else if (position == 1) {
                    buttons[0].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg1));
                    buttons[1].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg2));
                    buttons[2].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg1));
                }
                else if (position == 2) {
                    buttons[0].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg1));
                    buttons[1].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg1));
                    buttons[2].setBackground(ContextCompat.getDrawable(DetailActivity.this,R.drawable.bg2));
                }
            }
        });

        name1 = findViewById(R.id.detail_name1);
        name2 = findViewById(R.id.detail_name2);
        imdb = findViewById(R.id.detail_imdb);
        genre = findViewById(R.id.detail_genre);
        desc = findViewById(R.id.detail_desc);
        sessionTime = findViewById(R.id.buttonSession);
        bookSeats = findViewById(R.id.bookSeats);
        checkOut = findViewById(R.id.fixSeats);
        viewPagerLine = findViewById(R.id.viewPagerLineView);

        Intent intent = getIntent();
        String Sname = intent.getStringExtra("name");
        String Simdb = intent.getStringExtra("imdb");
        String Sgenre = intent.getStringExtra("genre");
        String Sdesc = intent.getStringExtra("desc");

        assert Sname != null;
        String[] name = Sname.split(" ");
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
        imdb.setText(Simdb);
        desc.setText(Sdesc);
        genre.setText(Sgenre);

        layouts = new RelativeLayout[5];
        layouts[0] = findViewById(R.id.timeView1);
        layouts[1] = findViewById(R.id.timeView2);
        layouts[2] = findViewById(R.id.timeView3);
        layouts[3] = findViewById(R.id.timeView4);
        layouts[4] = findViewById(R.id.timeView5);

        views = new View[5];
        views[0] = findViewById(R.id.timebar1);
        views[1] = findViewById(R.id.timebar2);
        views[2] = findViewById(R.id.timebar3);
        views[3] = findViewById(R.id.timebar4);
        views[4] = findViewById(R.id.timebar5);

        texts = new TextView[5];
        texts[0] = findViewById(R.id.time1);
        texts[1] = findViewById(R.id.time2);
        texts[2] = findViewById(R.id.time3);
        texts[3] = findViewById(R.id.time4);
        texts[4] = findViewById(R.id.time5);

        seatButtons = new Button[7][6];
        setSeatButton();
        for (int i = 0; i < 7; i++ ) {
            for (int j = 0; j < 6; j++) {
                seatButtons[i][j].setOnClickListener(seatButtonPressed);
            }
        }

        sessionTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Animation fadeIn = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.fade_in);
                final Animation fadeOut = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.fade_out);

                details.setAnimation(fadeOut);
                details.setVisibility(View.GONE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        session.setAnimation(fadeIn);
                        session.setVisibility(View.VISIBLE);
                    }
                }, 1000);

                for (int i = 0; i < 5; i++) {
                    layouts[i].setOnClickListener(timeSessionClicked);
                }
            }
        });

        bookSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeSelected.equals("")) {
                    Toast.makeText(DetailActivity.this, "Please select a time!", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Animation fadeIn = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.fade_in);
                    final Animation fadeOut = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.fade_out);

                    session.setAnimation(fadeOut);
                    session.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            detailCard.setVisibility(View.GONE);
                            seatCard.setAnimation(fadeIn);
                            seatCard.setVisibility(View.VISIBLE);
                            ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(DetailActivity.this, R.animator.flip);
                            anim.setTarget(viewPager2);
                            anim.setDuration(1000);
                            anim.start();
                        }
                    }, 1000);
                }
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation fadeIn = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.fade_in);
                fadeIn.setDuration(500);
                final Animation fadeOut = AnimationUtils.loadAnimation(DetailActivity.this, R.anim.fade_out);

                ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(DetailActivity.this, R.animator.flip_straight);
                anim.setTarget(viewPager2);
                anim.setDuration(1000);
                anim.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewPagerLine.setVisibility(View.VISIBLE);
                        viewPagerLine.setAnimation(fadeIn);
                    }
                }, 1000);

            }
        });
    }

    View.OnClickListener timeSessionClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout layout = (RelativeLayout) v;
            switch (layout.getId()) {
                case R.id.timeView1:
                    texts[0].setTextColor(Color.BLACK);
                    timeSelected = texts[0].getText().toString();
                    views[0].setBackgroundColor(Color.parseColor("#6200EE"));
                    for (int i = 1; i < 5; i++) {
                        views[i].setBackgroundColor(Color.parseColor("#B6B6B6"));
                        texts[i].setTextColor(Color.parseColor("#B6B6B6"));
                    }
                    break;
                case R.id.timeView2:
                    texts[1].setTextColor(Color.BLACK);
                    timeSelected = texts[1].getText().toString();
                    views[1].setBackgroundColor(Color.parseColor("#6200EE"));
                    for (int i = 0; i < 5; i++) {
                        if (i != 1) {
                            views[i].setBackgroundColor(Color.parseColor("#B6B6B6"));
                            texts[i].setTextColor(Color.parseColor("#B6B6B6"));
                        }
                    }
                    break;
                case R.id.timeView3:
                    texts[2].setTextColor(Color.BLACK);
                    timeSelected = texts[2].getText().toString();
                    views[2].setBackgroundColor(Color.parseColor("#6200EE"));
                    for (int i = 0; i < 5; i++) {
                        if (i != 2) {
                            views[i].setBackgroundColor(Color.parseColor("#B6B6B6"));
                            texts[i].setTextColor(Color.parseColor("#B6B6B6"));
                        }
                    }
                    break;
                case R.id.timeView4:
                    texts[3] = layout.findViewById(R.id.time4);
                    timeSelected = texts[3].getText().toString();
                    views[3].setBackgroundColor(Color.parseColor("#6200EE"));
                    for (int i = 0; i < 5; i++) {
                        if (i != 3) {
                            views[i].setBackgroundColor(Color.parseColor("#B6B6B6"));
                            texts[i].setTextColor(Color.parseColor("#B6B6B6"));
                        }
                    }
                    break;
                case R.id.timeView5:
                    texts[4] = layout.findViewById(R.id.time5);
                    timeSelected = texts[4].getText().toString();
                    views[4].setBackgroundColor(Color.parseColor("#6200EE"));
                    for (int i = 0; i < 4; i++) {
                        views[i].setBackgroundColor(Color.parseColor("#B6B6B6"));
                        texts[i].setTextColor(Color.parseColor("#B6B6B6"));
                    }
                    break;
            }
        }
    };

    private void setSeatButton() {
        seatButtons[0][0] = findViewById(R.id.A1);
        seatButtons[0][1] = findViewById(R.id.A2);
        seatButtons[0][2] = findViewById(R.id.A3);
        seatButtons[0][3] = findViewById(R.id.A4);seatButtons[0][4] = findViewById(R.id.A5);
        seatButtons[0][5] = findViewById(R.id.A6);
        seatButtons[1][0] = findViewById(R.id.B1);
        seatButtons[1][1] = findViewById(R.id.B2);
        seatButtons[1][2] = findViewById(R.id.B3);
        seatButtons[1][3] = findViewById(R.id.B4);seatButtons[1][4] = findViewById(R.id.B5);
        seatButtons[1][5] = findViewById(R.id.B6);
        seatButtons[2][0] = findViewById(R.id.C1);
        seatButtons[2][1] = findViewById(R.id.C2);
        seatButtons[2][2] = findViewById(R.id.C3);
        seatButtons[2][3] = findViewById(R.id.C4);seatButtons[2][4] = findViewById(R.id.C5);
        seatButtons[2][5] = findViewById(R.id.C6);
        seatButtons[3][0] = findViewById(R.id.D1);
        seatButtons[3][1] = findViewById(R.id.D2);
        seatButtons[3][2] = findViewById(R.id.D3);
        seatButtons[3][3] = findViewById(R.id.D4);seatButtons[3][4] = findViewById(R.id.D5);
        seatButtons[3][5] = findViewById(R.id.D6);
        seatButtons[4][0] = findViewById(R.id.E1);
        seatButtons[4][1] = findViewById(R.id.E2);
        seatButtons[4][2] = findViewById(R.id.E3);
        seatButtons[4][3] = findViewById(R.id.E4);seatButtons[4][4] = findViewById(R.id.E5);
        seatButtons[4][5] = findViewById(R.id.E6);
        seatButtons[5][0] = findViewById(R.id.F1);
        seatButtons[5][1] = findViewById(R.id.F2);
        seatButtons[5][2] = findViewById(R.id.F3);
        seatButtons[5][3] = findViewById(R.id.F4);seatButtons[5][4] = findViewById(R.id.F5);
        seatButtons[5][5] = findViewById(R.id.F6);
        seatButtons[6][0] = findViewById(R.id.G1);
        seatButtons[6][1] = findViewById(R.id.G2);
        seatButtons[6][2] = findViewById(R.id.G3);
        seatButtons[6][3] = findViewById(R.id.G4);seatButtons[6][4] = findViewById(R.id.G5);
        seatButtons[6][5] = findViewById(R.id.G6);
    }

    View.OnClickListener seatButtonPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            button.setBackground(ContextCompat.getDrawable(DetailActivity.this, R.drawable.bg2));
        }
    };
}
