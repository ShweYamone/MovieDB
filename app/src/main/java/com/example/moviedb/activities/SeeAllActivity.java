package com.example.moviedb.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.adapters.MovieAdapter;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.custom_control.MyanBoldTextView;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.mvp.presenter.SeeAllPresenterImpl;
import com.example.moviedb.mvp.view.SeeAllView;
import com.example.moviedb.util.ServiceHelper;

import java.util.List;

import butterknife.BindView;


public class SeeAllActivity extends BaseActivity implements SeeAllView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_text)
    MyanBoldTextView toolbar_text;

    @BindView(R.id.cv_data_error)
    CardView cvDataError;

    @BindView(R.id.recycler_movie)
    RecyclerView recyclerMovie;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MovieAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private SeeAllPresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private MyanProgressDialog mDialog;

    private static String movieTypes;

    private int page = 1;

    public static final String IE_SOMETHING = "KEY";
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_see_all;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

       // getSupportActionBar().setTitle(movieTypes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar_text.setMyanmarText(movieTypes);
        init();
    }

    public static Intent getMovieDetailActivityIntent(Context context, String movieType) {

        Intent intent = new Intent(context, SeeAllActivity.class);
        movieTypes = movieType;
        return intent;
    }

    private void init(){
        mAdapter = new MovieAdapter();

        mDialog = new MyanProgressDialog(this);

        mService = ServiceHelper.getClient(this);

        mPresenter = new SeeAllPresenterImpl(new MovieInteractor(mService));


        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {

                page++;
                if(movieTypes.equals("Upcoming"))
                    mPresenter.getUpcomingMoviesByPaging(page);
                else if(movieTypes.equals("Top Rated"))
                    mPresenter.getTopRatedMoviesByPaging(page);
                else if(movieTypes.equals("Popular"))
                    mPresenter.getPopularMoviesByPaging(page);
                else
                    mPresenter.getNowPlayingMoviesByPaging(page);
            }
        });

        recyclerMovie.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 3);

        recyclerMovie.setLayoutManager(manager);
        recyclerMovie.addItemDecoration(new ItemOffsetDecoration(3));
        recyclerMovie.setAdapter(mAdapter);
        recyclerMovie.addOnScrollListener(mSmartScrollListener);

        swipeRefreshLayout.setOnRefreshListener(this);

        mPresenter.onAttachView(this);
        mPresenter.onUIReady(movieTypes);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onTerminate();
    }

    @Override
    public void addMoreNowPlayingMoviesToTheList(List<MovieInfoModel> movieInfoModelList) {
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
    }

    @Override
    public void addMoreTopRatedMoviesToTheList(List<MovieInfoModel> movieInfoModelList) {
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
    }

    @Override
    public void addMoreUpcomingMoviesToTheList(List<MovieInfoModel> movieInfoModelList){
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
    }

    @Override
    public void addMorePopularMoviesToTheList(List<MovieInfoModel> movieInfoModelList) {
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
    }

    @Override
    public void showUpComingMovieList(List<MovieInfoModel> movieInfoModelList) {
        cvDataError.setVisibility(View.GONE);

        page = 1;

        mAdapter.clear();
        //mAdapter.showLoading();
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
        mAdapter.clearFooter();
    }

    @Override
    public void showTopRatedMovieList(List<MovieInfoModel> movieInfoModelList) {
        cvDataError.setVisibility(View.GONE);

        page = 1;

        mAdapter.clear();
        //mAdapter.showLoading();
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
        mAdapter.clearFooter();
    }

    @Override
    public void showNowShowingMovieList(List<MovieInfoModel> movieInfoModelList) {
        cvDataError.setVisibility(View.GONE);

        page = 1;

        mAdapter.clear();
        //mAdapter.showLoading();
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
        mAdapter.clearFooter();
    }

    @Override
    public void showPopularMovieList(List<MovieInfoModel> movieInfoModelList) {
        cvDataError.setVisibility(View.GONE);

        page = 1;

        mAdapter.clear();
        //mAdapter.showLoading();
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
        mAdapter.clearFooter();
    }
    @Override
    public void resetPageNumberToDefault() {
        page--;
    }

    @Override
    public void showNoMovieInfo() {
        cvDataError.setVisibility(View.VISIBLE);
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
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        this.mPresenter.getNowPlayingMovies();
    }
}
