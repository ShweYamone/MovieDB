package com.example.moviedb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.moviedb.R;
import com.example.moviedb.adapters.MovieAdapter;
import com.example.moviedb.adapters.MovieAdapter2;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.custom_control.BlurImage;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.MovieDetailInteractor;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.mvp.presenter.MovieDetailPresenter;
import com.example.moviedb.mvp.presenter.MovieDetailPresenterImpl;
import com.example.moviedb.mvp.view.MovieDetailView;
import com.example.moviedb.util.ServiceHelper;

import java.util.List;

import butterknife.BindView;

import static com.example.moviedb.util.AppConstant.BASE_IMG_URL;

public class MovieDetailActivity extends BaseActivity implements MovieDetailView {



    @BindView(R.id.movie_details_layout)
    ConstraintLayout movieDetailLayout;

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

    @BindView(R.id.txt_adult)
    TextView adult;

    @BindView(R.id.txt_duration)
    TextView duration;

    @BindView(R.id.txt_movie_overview)
    TextView movieOverview;

    @BindView(R.id.txt_movie_title)
    TextView movieTitle;

    @BindView(R.id.iv_bg)
    ImageView ivBackground;

    @BindView(R.id.similar_movie_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.iv_cancel)
    ImageView cancelbtn;


    private MyanProgressDialog mDialog;
    private MovieDetailPresenter mPresenter;
    private static int mmovieId;
    private MovieAdapter2 mAdapter;
    private MovieInfoModel movieInfoModel;
    private ServiceHelper.ApiService mService;
    public static Intent getMovieDetailActivityIntent(Context context, int movieId) {

        Intent intent = new Intent(context, MovieDetailActivity.class);
        mmovieId=movieId;
        return intent;
    }


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


        mService = ServiceHelper.getClient(this);
        mDialog = new MyanProgressDialog(this);


        mPresenter = new MovieDetailPresenterImpl(new MovieDetailInteractor(this.mService),new MovieInteractor(this.mService));


        mAdapter = new MovieAdapter2();
        recyclerView.setHasFixedSize(true);
        //recyclerMovie.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(2));
        recyclerView.setAdapter(mAdapter);
        mPresenter.onAttachView(this);
        mPresenter.onUIReady(mmovieId);


        playButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PlayMovieTrailer.gePlayMovieTrailerIntent(getApplicationContext(),mmovieId));
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void showLoading() {
        if (!isFinishing()) {
            mDialog.showDialog();
        }
    }

    @Override
    public void hideLoading() {


        if (!isFinishing()) {
            mDialog.hideDialog();
        }
    }

    @Override
    public void showToastMsg(String msg) {
        this.hideLoading();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialogMsg(String title, String msg) {
        this.hideLoading();
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(getString(R.string.ok), null).show();
    }

    @Override
    public void showMovieDetail(MovieInfoModel movieInfoModel) {

//        Glide.with(this)
//                .load(BASE_IMG_URL+movieInfoModel.getPoster_path())
//                .into(moviePoster);

        Glide.with(this)
                .asBitmap()
                .load(BASE_IMG_URL+movieInfoModel.getPoster_path())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap bitmapImage= BlurImage.fastblur(resource, (float) 0.08,5);
//                        movieDetailLayout.setBackground(new BitmapDrawable(getResources(), bitmapImage));

                        Glide.with(MovieDetailActivity.this)
                                .load(resource)
                                .into(moviePoster);

                        Glide.with(MovieDetailActivity.this)
                                .load(bitmapImage)
                                .into(ivBackground);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
//        Bitmap bitmapImage= BlurImage.fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.maleficent), (float) 0.08,5);
//        Bitmap bm=((BitmapDrawable)moviePoster.getDrawable()).getBitmap();
        //Bitmap bm= BlurImage.fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.maleficent), (float) 0.08,5);

//
//        mtMovieTitle.setMyanmarText(model.getTitle());

        movieTitle.setText(movieInfoModel.getTitle());
        releaseDate.setText(movieInfoModel.getRelease_date());
        String strIsAdult="";
        if(movieInfoModel.isAdult()== true){
            strIsAdult="18+";
            adult.setText(strIsAdult);
        }
        else{
            adult.setText("");
        }
        int runtimeMins=movieInfoModel.getRuntime();
        int hr=runtimeMins/60;
        int mins=runtimeMins-(hr*60);
        String strmins="";
        if(mins<10){
           strmins= "0"+mins+" min";
        }else{
            strmins=mins+""+" min";
        }

        duration.setText(hr+" hr "+strmins);
        movieOverview.setText(movieInfoModel.getOverview());

    }

    @Override
    public void showSimilarVideos(List<MovieInfoModel> similarVideoListModel) {
        mAdapter.clear();
        // mAdapter.showLoading();
        for (MovieInfoModel model: similarVideoListModel) {
            mAdapter.add(model);
        }
    }
}
