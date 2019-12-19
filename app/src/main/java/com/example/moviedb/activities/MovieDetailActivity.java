package com.example.moviedb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.moviedb.DB.InitializeDatabase;
import com.example.moviedb.R;
import com.example.moviedb.adapters.MovieAdapter2;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.custom_control.BlurImage;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.delegate.MovieDelegate;
import com.example.moviedb.interactor.MovieDetailInteractor;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.interactor.WatchListInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieRateBody;
import com.example.moviedb.model.MovieRateInfoModel;
import com.example.moviedb.model.WatchListBody;
import com.example.moviedb.mvp.presenter.MovieDetailPresenter;
import com.example.moviedb.mvp.presenter.MovieDetailPresenterImpl;
import com.example.moviedb.mvp.view.MovieDetailView;
import com.example.moviedb.util.Network;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import butterknife.BindView;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static com.example.moviedb.util.AppConstant.BASE_IMG_URL;

public class MovieDetailActivity extends BaseActivity implements MovieDelegate, MovieDetailView {



    @BindView(R.id.movie_details_layout)
    ConstraintLayout movieDetailLayout;

    @BindView(R.id.play_button_layout)
    LinearLayout playButtonLayout;

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
    RecyclerView similarMovieRecyclerView;

    @BindView(R.id.recommend_movie_recycler_view)
    RecyclerView recommendMovieRecycleView;

    @BindView(R.id.iv_cancel)
    ImageButton cancelbtn;

    @BindView(R.id.iv_plus)
    ImageView plusbtn;

    @BindView(R.id.iv_rate)
    ImageView ratebtn;

    @BindView(R.id.tv_MoreLikeThis)
    TextView labelMoreLikeThis;

    @BindView(R.id.tv_Recommendation)
    TextView labelRecommendation;

    @BindView(R.id.btn_myList_layout)
    LinearLayout btnMyList;

    @BindView(R.id.btn_rate_layout)
    LinearLayout btnRate;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;



    private MyanProgressDialog mDialog;
    private MovieDetailPresenter mPresenter;
    private static int mmovieId;
    private MovieAdapter2 _SIMILAR;
    private MovieAdapter2 _RECOMMEND;
    private ServiceHelper.ApiService mService;
    private String sessionId;
    public InitializeDatabase dbHelper;
    private int movieCount;
    private int ratedMovieCount;
    private SharePreferenceHelper sharePreferenceHelper;
    private int accountId;
    private int countOfMovie;
    private String strMovieName,strReleaseDate,strIsAdult,strDuration,strOverview;
    private boolean blisAdult;
    private Network mNetwork;
    private String moviePosterPath;



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
        sharePreferenceHelper=new SharePreferenceHelper(this);

        mNetwork = new Network(context());

        accountId=sharePreferenceHelper.getUserId();
        sessionId=sharePreferenceHelper.getSessionId();
        dbHelper = InitializeDatabase.getInstance(MovieDetailActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //if (true) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            } else {
//                // We want to change tint color to white again.
//                // You can also record the flags in advance so that you can turn UI back completely if
//                // you have set other flags before, such as translucent or full screen.
//                decor.setSystemUiVisibility(0);
//            }
        }
        init();
    }

    private void init(){
        //check condition to change list icon
        movieCount=dbHelper.myListDAO().getMoviebyId(mmovieId,accountId);
        if(movieCount==1){

            changeMyListIcon("checkIcon");

        }
        else if (movieCount==0){

            changeMyListIcon("plusIcon");
        }

         //check condition to show rating
        ratedMovieCount=dbHelper.myRateListDAO().getRatedMovieCountbyId(mmovieId,accountId);

        if(ratedMovieCount==1){
            ratingBar.setRating( (Float.parseFloat(dbHelper.myRateListDAO().getRatedValueByMovieId(mmovieId,accountId)/2.0+"")));
        }
        else if (ratedMovieCount==0){
            ratingBar.setVisibility(View.GONE);
        }





        mService = ServiceHelper.getClient(this);
        mDialog = new MyanProgressDialog(this);


        mPresenter = new MovieDetailPresenterImpl(new MovieDetailInteractor(this.mService),new MovieInteractor(this.mService),new WatchListInteractor(this.mService));


        _SIMILAR = new MovieAdapter2(this);
        _RECOMMEND=new MovieAdapter2(this);
        similarMovieRecyclerView.setHasFixedSize(true);
        similarMovieRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        similarMovieRecyclerView.addItemDecoration(new ItemOffsetDecoration(2));
        similarMovieRecyclerView.setAdapter(_SIMILAR);

        recommendMovieRecycleView.setHasFixedSize(true);
        recommendMovieRecycleView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recommendMovieRecycleView.addItemDecoration(new ItemOffsetDecoration(2));
        recommendMovieRecycleView.setAdapter(_RECOMMEND);


        mPresenter.onAttachView(this);

        //if internet isn't availabel ,load data from db

        if (!mNetwork.isNetworkAvailable()) {

            ViewGroup viewGroup = findViewById(android.R.id.content);

            //then we will inflate the custom alert dialog xml that we created
            View dialogView = LayoutInflater.from(MovieDetailActivity.this).inflate(R.layout.custom_dialog_no_internet, viewGroup, false);

            //Now we need an AlertDialog.Builder object
            AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);

            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);

            //finally creating the alert dialog and displaying it
            AlertDialog alertDialog = builder.create();


            // if button is clicked, close the custom dialog
            TextView ok = dialogView.findViewById(R.id.tv_ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();

            int countInRateInfoModel=dbHelper.myRateListDAO().getRatedMovieCountbyId(mmovieId,accountId);
            int countInListInfoModel=dbHelper.myListDAO().getMoviebyId(mmovieId,accountId);

            if(countInRateInfoModel==1) {
                MovieRateInfoModel movie = dbHelper.myRateListDAO().getMovie(mmovieId, accountId);

                movieTitle.setText(movie.getTitle());
                releaseDate.setText(movie.getRelease_date());

                if (movie.isAdult()) {
                    adult.setText("18+");
                } else {
                    adult.setText("");
                    adult.setVisibility(View.GONE);
                }

                duration.setVisibility(View.GONE);
                movieOverview.setText(movie.getOverview());
                float rateValue = dbHelper.myRateListDAO().getRatedValueByMovieId(mmovieId, accountId);
                ratingBar.setRating((float) (rateValue / 2.0));

                Glide.with(MovieDetailActivity.this)
                        .load(R.drawable.img_placeholder)
                        .into(moviePoster);
                Bitmap bitmapImage = BlurImage.fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.img_placeholder), (float) 0.08, 5);

                Glide.with(MovieDetailActivity.this)
                        .load(bitmapImage)
                        .into(ivBackground);


                hideLabelMoreLikeThis();
                hideLabelRecommendation();
            }else if(countInListInfoModel==1){
                MovieInfoModel movie = dbHelper.myListDAO().getMovie(mmovieId,accountId);

                movieTitle.setText(movie.getTitle());
                releaseDate.setText(movie.getRelease_date());

                if (movie.isAdult()) {
                    adult.setText("18+");
                } else {
                    adult.setText("");
                    adult.setVisibility(View.GONE);
                }

                duration.setVisibility(View.GONE);
                movieOverview.setText(movie.getOverview());
                float rateValue = dbHelper.myRateListDAO().getRatedValueByMovieId(mmovieId, accountId);
                ratingBar.setRating((float) (rateValue / 2.0));

                Glide.with(MovieDetailActivity.this)
                        .load(R.drawable.img_placeholder)
                        .into(moviePoster);
                Bitmap bitmapImage = BlurImage.fastblur(BitmapFactory.decodeResource(getResources(), R.drawable.img_placeholder), (float) 0.08, 5);

                Glide.with(MovieDetailActivity.this)
                        .load(bitmapImage)
                        .into(ivBackground);


                hideLabelMoreLikeThis();
                hideLabelRecommendation();
            }

        }
        else {

            mPresenter.onUIReady(mmovieId);
        }

        //play trailer movie
        playButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PlayMovieTrailerActivity.gePlayMovieTrailerIntent(MovieDetailActivity.this,mmovieId));
            }
        });

        //Back to previous activity
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //add to watchlist
        btnMyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //network not available, can't do any actions with mylist and rate
                if (!mNetwork.isNetworkAvailable()) {
                    Snackbar.make(v.getRootView(),"Sorry, you're offline", Snackbar.LENGTH_SHORT).show();
                }
                else{

                    if(sharePreferenceHelper.isLogin()){
                        movieCount=dbHelper.myListDAO().getMoviebyId(mmovieId,accountId);

                            if(movieCount==1){

                                mPresenter.addOrRemoveMovieFromWatchList(accountId,sessionId,new WatchListBody("movie",mmovieId,false));
                                dbHelper.myListDAO().deleteById(mmovieId,accountId);
                                changeMyListIcon("plusIcon");


                            }else if (movieCount==0) {

                                mPresenter.addOrRemoveMovieFromWatchList(accountId,sessionId, new WatchListBody("movie", mmovieId, true));

                                strMovieName = movieTitle.getText().toString();
                                strReleaseDate = releaseDate.getText().toString();
                                strIsAdult = adult.getText().toString();
                                strDuration = duration.getText().toString();
                                strOverview = movieOverview.getText().toString();
                                if (strIsAdult == "") {
                                    blisAdult = false;
                                } else {
                                    blisAdult = true;
                                }

                                //add movie to movieInfoModel

                                dbHelper.myListDAO().insert(new MovieInfoModel(mmovieId,accountId,blisAdult,strReleaseDate,strMovieName,moviePosterPath,strOverview));

                                changeMyListIcon("checkIcon");
                            }

                        }
                        else{


                            ViewGroup viewGroup = findViewById(android.R.id.content);

                            //then we will inflate the custom alert dialog xml that we created
                            View dialogView = LayoutInflater.from(MovieDetailActivity.this).inflate(R.layout.custom_dialog1, viewGroup, false);

                            //Now we need an AlertDialog.Builder object
                            AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);

                            //setting the view of the builder to our custom view that we already inflated
                            builder.setView(dialogView);

                            //finally creating the alert dialog and displaying it
                            AlertDialog alertDialog = builder.create();


                            TextView canecel = dialogView.findViewById(R.id.tv_cancel);
                            canecel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });

                            // if button is clicked, close the custom dialog
                            TextView ok = dialogView.findViewById(R.id.tv_ok);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    startActivity(LoginActivity.getLoginActivityIntent(context()));
                                    alertDialog.dismiss();
                                }
                            });

                            alertDialog.show();

                        }



                }


            }
        });

        //rate movie
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!mNetwork.isNetworkAvailable()) {
                    Snackbar.make(v.getRootView(),"Sorry, you're offline", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    if (sharePreferenceHelper.isLogin()) {


                        //   mPresenter.rateMovie(mmovieId,sessionId,new MovieRateBody(4.0f));
                        // custom dialog

                        ViewGroup viewGroup = findViewById(android.R.id.content);

                        //then we will inflate the custom alert dialog xml that we created
                        View dialogView = LayoutInflater.from(MovieDetailActivity.this).inflate(R.layout.custom_dialog, viewGroup, false);


                        //Now we need an AlertDialog.Builder object
                        AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);

                        //setting the view of the builder to our custom view that we already inflated
                        builder.setView(dialogView);

                        //finally creating the alert dialog and displaying it
                        AlertDialog alertDialog = builder.create();


                        RatingBar ratingBarInDailog = dialogView.findViewById(R.id.rb_rate);

                        ratingBarInDailog.setRating((float) (dbHelper.myRateListDAO().getRatedValueByMovieId(mmovieId,accountId)/2.0));

                        // if button is clicked, close the custom dialog
                        Button dialogButton = dialogView.findViewById(R.id.buttonOk);
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                float rateValue = (float) (ratingBarInDailog.getRating() * 2.0);
                                //if rateValue is 0 ,user is intended to delete the rating
                                //else update the rate value
                                // and rated movie will also remove from watchlist by the moviedb law

                                if (rateValue == 0) {
                                    mPresenter.deleteRating(mmovieId, sessionId);

                                    ratedMovieCount = dbHelper.myRateListDAO().getRatedMovieCountbyId(mmovieId, accountId);
                                    if (ratedMovieCount == 1) {
                                        dbHelper.myRateListDAO().deleteByMovieId(mmovieId, accountId);
                                        ratingBar.setVisibility(View.GONE);
                                    }

                                } else {
                                    //add rating to the moviedb website
                                    mPresenter.rateMovie(mmovieId, sessionId, new MovieRateBody(rateValue));
                                    //if the rated movie is in watchlist ,it will be deleted.

                                     //rated movie will be removed from watchlist
                                    int countOfMovieInWatchList=dbHelper.myListDAO().getMoviebyId(mmovieId,accountId);
                                    if(countOfMovieInWatchList==1){
                                        mPresenter.addOrRemoveMovieFromWatchList(accountId,sessionId,new WatchListBody("movie",mmovieId,false));
                                        dbHelper.myListDAO().deleteById(mmovieId,accountId);
                                        changeMyListIcon("plusIcon");
                                    }


                                        //add movie to movierateinfomodel
                                    strMovieName = movieTitle.getText().toString();
                                    strReleaseDate = releaseDate.getText().toString();
                                    strIsAdult = adult.getText().toString();
                                    strDuration = duration.getText().toString();
                                    strOverview = movieOverview.getText().toString();
                                    if (strIsAdult == "") {
                                        blisAdult = false;
                                    } else {
                                        blisAdult = true;
                                    }

                                    //add movie info to local rate list table
                                    ratedMovieCount = dbHelper.myRateListDAO().getRatedMovieCountbyId(mmovieId, accountId);
                                    if (ratedMovieCount == 1) {
                                        dbHelper.myRateListDAO().updateRateListByMovieId(mmovieId, accountId, rateValue);
                                    }
                                    else if (ratedMovieCount == 0) {

                                        dbHelper.myRateListDAO().insert(new MovieRateInfoModel(mmovieId,accountId,blisAdult,strReleaseDate,strMovieName,moviePosterPath,rateValue,strOverview));
                                    }

                                    ratingBar.setVisibility(View.VISIBLE);
                                    ratingBar.setRating((float) (rateValue / 2.0));
                                }


                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    } else {

                        ViewGroup viewGroup = findViewById(android.R.id.content);

                        //then we will inflate the custom alert dialog xml that we created
                        View dialogView = LayoutInflater.from(MovieDetailActivity.this).inflate(R.layout.custom_dialog1, viewGroup, false);


                        //Now we need an AlertDialog.Builder object
                        AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);

                        //setting the view of the builder to our custom view that we already inflated
                        builder.setView(dialogView);

                        //finally creating the alert dialog and displaying it
                        AlertDialog alertDialog = builder.create();


                        TextView canecel = dialogView.findViewById(R.id.tv_cancel);
                        canecel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });

                        // if button is clicked, close the custom dialog
                        TextView ok = dialogView.findViewById(R.id.tv_ok);
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                startActivity(LoginActivity.getLoginActivityIntent(context()));
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();

                    }
                }
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

        moviePosterPath=movieInfoModel.getPoster_path();

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
        if(movieInfoModel.isAdult()){
            strIsAdult="18+";
            adult.setText(strIsAdult);
        }
        else{
            adult.setText("");
            adult.setVisibility(View.GONE);
        }
        int runtimeMins=movieInfoModel.getRuntime();
        int hr=runtimeMins/60;
        int mins=runtimeMins-(hr*60);
        String strmins;
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
        _SIMILAR.clear();
        // mAdapter.showLoading();
        for (MovieInfoModel model: similarVideoListModel) {
            _SIMILAR.add(model);
        }
    }

    @Override
    public void showRecommendedVideos(List<MovieInfoModel> recommendedVideoListModel) {
        _RECOMMEND.clear();
        // mAdapter.showLoading();
        for (MovieInfoModel model : recommendedVideoListModel) {
            _RECOMMEND.add(model);
        }
    }

    @Override
    public void hideLabelMoreLikeThis() {
        labelMoreLikeThis.setVisibility(View.GONE);
    }

    @Override
    public void hideLabelRecommendation() {
        labelRecommendation.setVisibility(View.GONE);
    }

    @Override
    public void changeMyListIcon(String status) {
        if(status.equals("checkIcon")){
            plusbtn.setImageResource(R.drawable.icons8_checked_24);
        }
        else if(status.equals("plusIcon")){
            plusbtn.setImageResource(R.drawable.icons8_plus_24);
        }


    }

    @Override
    public void onGiveRemark(int movieId) {

    }
}
