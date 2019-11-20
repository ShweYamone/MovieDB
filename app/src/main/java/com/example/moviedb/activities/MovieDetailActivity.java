package com.example.moviedb.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.moviedb.DB.InitializeDatabase;
import com.example.moviedb.Entity.MyList;
import com.example.moviedb.Entity.MyRateList;
import com.example.moviedb.R;
import com.example.moviedb.adapters.MovieAdapter2;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.custom_control.BlurImage;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.MovieDetailInteractor;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.interactor.WatchListInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieRateBody;
import com.example.moviedb.model.WatchListBody;
import com.example.moviedb.mvp.presenter.MovieDetailPresenter;
import com.example.moviedb.mvp.presenter.MovieDetailPresenterImpl;
import com.example.moviedb.mvp.view.MovieDetailView;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;

import java.util.List;

import butterknife.BindView;

import static com.example.moviedb.util.AppConstant.BASE_IMG_URL;

public class MovieDetailActivity extends BaseActivity implements MovieDetailView {



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
    private MovieInfoModel movieInfoModel;
    private ServiceHelper.ApiService mService;
    private String sessionId;
    public InitializeDatabase dbHelper;
    private int movieCount;
    private int ratedMovieCount;
    private SharePreferenceHelper sharePreferenceHelper;
    private int accountId;


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
        accountId=sharePreferenceHelper.getUserId();
        sessionId=sharePreferenceHelper.getSessionId();
        dbHelper = InitializeDatabase.getInstance(MovieDetailActivity.this);
        init();
    }

    private void init(){

        movieCount=dbHelper.myListDAO().getMoviebyId(mmovieId,accountId);
        if(movieCount==1){

            changeMyListIcon("checkIcon");

        }
        else if (movieCount==0){

            changeMyListIcon("plusIcon");
        }


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


        _SIMILAR = new MovieAdapter2();
        _RECOMMEND=new MovieAdapter2();
        similarMovieRecyclerView.setHasFixedSize(true);
        similarMovieRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        similarMovieRecyclerView.addItemDecoration(new ItemOffsetDecoration(2));
        similarMovieRecyclerView.setAdapter(_SIMILAR);

        recommendMovieRecycleView.setHasFixedSize(true);
        recommendMovieRecycleView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recommendMovieRecycleView.addItemDecoration(new ItemOffsetDecoration(2));
        recommendMovieRecycleView.setAdapter(_RECOMMEND);


        mPresenter.onAttachView(this);
        mPresenter.onUIReady(mmovieId);


        playButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PlayMovieTrailerActivity.gePlayMovieTrailerIntent(getApplicationContext(),mmovieId));
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sharePreferenceHelper.isLogin()){
                    movieCount=dbHelper.myListDAO().getMoviebyId(mmovieId,accountId);
                    showToastMsg(movieCount+"");

                    if(movieCount==1){

                        mPresenter.addOrRemoveMovieFromWatchList(sessionId,new WatchListBody("movie",mmovieId,false));
                        dbHelper.myListDAO().deleteById(mmovieId,accountId);
                        changeMyListIcon("plusIcon");

                    }
                    else if(movieCount==0) {

                        mPresenter.addOrRemoveMovieFromWatchList(sessionId,new WatchListBody("movie",mmovieId,true));
                        dbHelper.myListDAO().insert(new MyList(mmovieId,accountId));
                        changeMyListIcon("checkIcon");
                    }
                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);



                    // Ask the final question
                    builder.setMessage("Please Login to continue!!!");

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when user clicked the Yes button
                            // Set the TextView visibility GONE
                            startActivity(LoginActivity.getLoginActivityIntent(context()));

                        }
                    });

                    // Set the alert dialog no button click listener
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when No button clicked
                            Toast.makeText(getApplicationContext(),
                                    "' No' Button Clicked",Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();

                }

            }
        });


        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sharePreferenceHelper.isLogin()){


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

                    // if button is clicked, close the custom dialog
                    TextView dialogButton = dialogView.findViewById(R.id.buttonOk);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            float rateValue=(float) (ratingBarInDailog.getRating()*2.0);
                            if(rateValue==0){
                               mPresenter.deleteRating(mmovieId,sessionId);

                                ratedMovieCount=dbHelper.myRateListDAO().getRatedMovieCountbyId(mmovieId,accountId);
                                if(ratedMovieCount==1){
                                    dbHelper.myRateListDAO().deleteByMovieId(mmovieId,accountId);
                                    ratingBar.setVisibility(View.GONE);
                                }

                            }else{
                                mPresenter.rateMovie(mmovieId,sessionId,new MovieRateBody(rateValue));
                                ratedMovieCount=dbHelper.myRateListDAO().getRatedMovieCountbyId(mmovieId,accountId);
                                if(ratedMovieCount==1){
                                    dbHelper.myRateListDAO().updateRateListByMovieId(mmovieId,accountId,rateValue);
                                }
                                else if (ratedMovieCount==0){
                                    dbHelper.myRateListDAO().insert(new MyRateList(mmovieId,rateValue,accountId));
                                }

                                ratingBar.setVisibility(View.VISIBLE);
                                ratingBar.setRating((float) (rateValue/2.0));
                            }


                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();
                }
                else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(MovieDetailActivity.this);



                    // Ask the final question
                    builder.setMessage("Please Login to continue!!!");

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when user clicked the Yes button
                            // Set the TextView visibility GONE
                            startActivity(LoginActivity.getLoginActivityIntent(context()));

                        }
                    });

                    // Set the alert dialog no button click listener
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something when No button clicked
                            Toast.makeText(getApplicationContext(),
                                    "' No' Button Clicked",Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();

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
        if(status=="checkIcon"){
            plusbtn.setImageResource(R.drawable.icons8_checked_24);
        }
        else if(status=="plusIcon"){
            plusbtn.setImageResource(R.drawable.icons8_plus_24);
        }


    }
}
