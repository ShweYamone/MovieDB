package com.example.moviedb.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moviedb.R;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.custom_control.BlurImage;

import butterknife.BindView;

public class MovieDetailActivity extends BaseActivity {



    @BindView(R.id.movie_details_layout)
    LinearLayout movieDetailLayout;
    @BindView(R.id.play_button_layout)
    LinearLayout playButtonLayout;
    @BindView(R.id.btn_myList_layout)
    LinearLayout myListLayout;
    @BindView(R.id.btn_rate_layout)
    LinearLayout rateLayout;

    @BindView(R.id.iv_movie_poster)
    ImageView moviePoster;

    @BindView(R.id.txt_release_date)
    TextView releaseDate;

    @BindView(R.id.txt_is_adult)
    TextView isAdult;

    @BindView(R.id.txt_duration)
    TextView duration;

    @BindView(R.id.txt_movie_overview)
    TextView movieOverview;



    @Override
    protected int getLayoutResource() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        //setupToolbar(false);
        init();
    }

    private void init(){
        Bitmap bitmapImage= BlurImage.fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.maleficent), (float) 0.08,5);
        movieDetailLayout.setBackground(new BitmapDrawable(getResources(), bitmapImage));
    }
}
