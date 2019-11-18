package com.example.moviedb.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.moviedb.R;
import com.example.moviedb.activities.SeeAllActivity;
import com.example.moviedb.adapters.MovieAdapter2;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.mvp.presenter.HomePresenterImpl;
import com.example.moviedb.mvp.view.HomeView;
import com.example.moviedb.util.ServiceHelper;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import butterknife.BindView;
import retrofit2.http.GET;

public class HomeFragment extends BaseFragment implements HomeView {



    SmartScrollListener smartScrollListener;

    @BindView(R.id.rv_now_playing)
    RecyclerView recyclerNowPlaying;

    @BindView(R.id.rv_top_rated)
    RecyclerView recyclerTopRated;

    @BindView(R.id.rv_popular)
    RecyclerView recyclerPopular;

    @BindView(R.id.rv_upcoming)
    RecyclerView recyclerUpcoming;

    @BindView(R.id.btn_now_playing)
    Button btnNowPlaying;

    @BindView(R.id.btn_top_rated)
    Button btnTopRated;

    @BindView(R.id.btn_popular)
    Button btnPopular;

    @BindView(R.id.btn_upcoming)
    Button btnUpcoming;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.sv_home)
    ScrollView scrollView;

    private MovieAdapter2 _NowShowing, _Popular, _TopRated, _Upcoming;

    private ServiceHelper.ApiService mService;

    private HomePresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private MyanProgressDialog mDialog;

    private int page = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        _NowShowing = new MovieAdapter2();
        _Popular = new MovieAdapter2();
        _TopRated = new MovieAdapter2();
        _Upcoming = new MovieAdapter2();

        mDialog = new MyanProgressDialog(this.getActivity());

        mService = ServiceHelper.getClient(this.getActivity());

        mPresenter = new HomePresenterImpl(new MovieInteractor(mService));
        init();

    }

    private void init() {

        //Recycler Now Playing
        recyclerNowPlaying.setHasFixedSize(true);
        recyclerNowPlaying.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNowPlaying.addItemDecoration(new ItemOffsetDecoration(2));
        recyclerNowPlaying.setAdapter(_NowShowing);

        //Recycler popular
        recyclerPopular.setHasFixedSize(true);
        recyclerPopular.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerPopular.addItemDecoration(new ItemOffsetDecoration(2));
        recyclerPopular.setAdapter(_Popular);

        //Recycler Upcoming
        recyclerUpcoming.setHasFixedSize(true);
        recyclerUpcoming.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclerUpcoming.addItemDecoration(new ItemOffsetDecoration(2));
        recyclerUpcoming.setAdapter(_Upcoming);

        //Recycler Top rated
        recyclerTopRated.setHasFixedSize(true);
        recyclerTopRated.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerTopRated.addItemDecoration(new ItemOffsetDecoration(2));
        recyclerTopRated.setAdapter(_TopRated);

        shimmerFrameLayout.startShimmerAnimation();
        mPresenter.onAttachView(this);
        mPresenter.onUIReady();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        }, 500);

        btnNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(SeeAllActivity.getMovieDetailActivityIntent(getContext(),"Now Playing"));
            }
        });

        btnPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(SeeAllActivity.getMovieDetailActivityIntent(getContext(),"Popular"));
            }
        });

        btnTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(SeeAllActivity.getMovieDetailActivityIntent(getContext(),"Top Rated"));
            }
        });

        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(SeeAllActivity.getMovieDetailActivityIntent(getContext(),"Upcoming"));
            }
        });
        //shimmerFrameLayout.setVisibility(View.GONE);

    }

    @Override
    public void showUpComingMovieList(List<MovieInfoModel> movieInfoModelList){
        _Upcoming.clear();
        for(MovieInfoModel model: movieInfoModelList){
            _Upcoming.add(model);
        }

    }

    @Override
    public void showPopularMovieList(List<MovieInfoModel> movieInfoModelList){
        _Popular.clear();
        for(MovieInfoModel model: movieInfoModelList){
            _Popular.add(model);
        }

    }

    @Override
    public void showTopRatedMovieList(List<MovieInfoModel> movieInfoModelList){
        _TopRated.clear();
        for(MovieInfoModel model: movieInfoModelList){
            _TopRated.add(model);
        }

    }

    @Override
    public void showNowShowingMovieList(List<MovieInfoModel> movieInfoModelList){
        _NowShowing.clear();
        //   mAdapter.showLoading();
        for (MovieInfoModel model: movieInfoModelList) {
            _NowShowing.add(model);
        }

    }

    @Override
    public void resetPageNumberToDefault(){

    }

    @Override
    public void showNoMovieInfo(){

    }

    @Override
    public Context context() {
        return this.getActivity();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToastMsg(String msg) {

    }

    @Override
    public void showDialogMsg(String title, String msg) {

    }
}
