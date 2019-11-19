package com.example.moviedb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.GetVideoResultInteractor;
import com.example.moviedb.model.GetVideoResultModel;
import com.example.moviedb.model.MovieTrailerModel;
import com.example.moviedb.mvp.presenter.MovieTrailerPresenter;
import com.example.moviedb.mvp.presenter.MovieTrailerPresenterImpl;
import com.example.moviedb.mvp.view.MovieTrailerView;
import com.example.moviedb.util.ServiceHelper;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import butterknife.BindView;

public class PlayMovieTrailerActivity extends BaseActivity implements MovieTrailerView {

    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;

    private int movieId;
    private String strMovieId="";
    private MyanProgressDialog mDialog;
    private ServiceHelper.ApiService mService;
    private MovieTrailerModel movieTrailerModel;
    private MovieTrailerPresenter movieTrailerPresenter;
    private GetVideoResultInteractor getVideoResultInteractor;
    private GetVideoResultModel getVideoResultModel;

    private static final String TAG = "PlayMovieTrailerActivity";

    private static final String IE_MOVIE_ID = "movieid";

    public static Intent gePlayMovieTrailerIntent(Context context, int movieId) {

        Intent intent = new Intent(context, PlayMovieTrailerActivity.class);
        intent.putExtra(IE_MOVIE_ID,movieId);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_play_movie_trailer;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init();
    }

    private void init(){
        //movieId=420809;
//        Toast.makeText(PlayMovieTrailerActivity.this,movieId,Toast.LENGTH_LONG);

        movieId = getIntent().getIntExtra(IE_MOVIE_ID, 0);

       //Log.e(TAG, "init: " + movieId );
        mService = ServiceHelper.getClient(this);
        mDialog = new MyanProgressDialog(this);
        //  MovieTrailerModel movieTrailerModel=movieTrailerInteractor.getVideoById(movieId);
        movieTrailerPresenter = new MovieTrailerPresenterImpl(new GetVideoResultInteractor(this.mService));
        movieTrailerPresenter.onAttachView(this);
        movieTrailerPresenter.getVideo(movieId);

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
    public void hideLoading( ) {
        if (!isFinishing()) {
            mDialog.hideDialog();
        }

    }

    @Override
    public void showToastMsg( String msg) {
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
    public void showVideo(GetVideoResultModel getVideoResultModel) {
        strMovieId=getVideoResultModel.getResults().get(0).getKey();

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {


//                YouTubePlayerUtils.loadOrCueVideo(
//                        youTubePlayer,
//                        getLifecycle(),
//                        strMovieId,
//                        0
//                );


                youTubePlayer.loadVideo(strMovieId, 0);
            }
        });
    }
}
