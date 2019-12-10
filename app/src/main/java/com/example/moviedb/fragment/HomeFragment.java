package com.example.moviedb.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.moviedb.R;
import com.example.moviedb.activities.SeeAllActivity;
import com.example.moviedb.adapters.CustomAdapter;
import com.example.moviedb.adapters.MovieAdapter2;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.CirclePagerIndicatorDecoration;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.delegate.MovieDelegate;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.mvp.presenter.HomePresenterImpl;
import com.example.moviedb.mvp.view.HomeView;
import com.example.moviedb.util.Network;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.http.GET;

public class HomeFragment extends BaseFragment implements MovieDelegate ,HomeView , SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.layoutNotInternetShow)
    LinearLayout layoutForNoInternet;

    @BindView(R.id.layoutHasInternetShow)
    LinearLayout layoutForHavingInternet;

    @BindView(R.id.rv_now_playing)
    RecyclerView recyclerNowPlaying;

    @BindView(R.id.rv_top_rated)
    RecyclerView recyclerTopRated;

    @BindView(R.id.rv_custom)
    com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView recyclerCustom;

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
    NestedScrollView scrollView;


    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MovieAdapter2 _NowShowing, _Popular, _TopRated, _Upcoming;
    private CustomAdapter _custom;

    private ServiceHelper.ApiService mService;

    private HomePresenterImpl mPresenter;

    private MyanProgressDialog mDialog;

    private LinearLayoutManager linearLayoutManager;

    private SharePreferenceHelper mSharePreferenceHelper;
    private String mSessionId;
    private int mAccountId;

    private Network mNetwork;

    private int page = 1;
    private int position = 0;
    private boolean forward = true;

    private static final String TAG = "HomeFragment";
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        _NowShowing = new MovieAdapter2(this);
        _Popular = new MovieAdapter2(this);
        _TopRated = new MovieAdapter2(this);
        _Upcoming = new MovieAdapter2(this);
        _custom = new CustomAdapter();

        mDialog = new MyanProgressDialog(this.getActivity());

        mService = ServiceHelper.getClient(this.getActivity());

        mSharePreferenceHelper = new SharePreferenceHelper(context());
        mSessionId = mSharePreferenceHelper.getSessionId();
        mAccountId = mSharePreferenceHelper.getUserId();
        Log.i("accountInHomeFrag", mAccountId + "");
        mPresenter = new HomePresenterImpl(new MovieInteractor(mService), mSessionId, mAccountId);
        init();

    }

    private void init() {

        mNetwork = new Network(context());

        //no internet connection
        if (!mNetwork.isNetworkAvailable()) {
            layoutForHavingInternet.setVisibility(View.GONE);
            layoutForNoInternet.setVisibility(View.VISIBLE);
        }

        //have internet connection
        else {
            mPresenter.onAttachView(this);
            if(mSharePreferenceHelper.isLogin()) {
                mPresenter.locateDataFromApi();
            }

            layoutForHavingInternet.setVisibility(View.VISIBLE);
            layoutForNoInternet.setVisibility(View.GONE);

            //Recycler custom
            linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL,false);
            recyclerCustom.setHasFixedSize(true);
            recyclerCustom.setLayoutManager(linearLayoutManager);
            // add pager behavior
            PagerSnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerCustom);
            recyclerCustom.addItemDecoration(new CirclePagerIndicatorDecoration());
            recyclerCustom.setAdapter(_custom);
            addCustomPhotos();
            autoScroll();


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

            swipeRefreshLayout.setOnRefreshListener(this);
            shimmerFrameLayout.startShimmerAnimation();


            //For rate and watchlist
            mSharePreferenceHelper = new SharePreferenceHelper(context());
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
        }



    }

    private void autoScroll() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                position = linearLayoutManager.findFirstVisibleItemPosition();

                if(forward) {
                    recyclerCustom.smoothScrollToPosition(++position);
                    if (position >= _custom.getItemCount() - 1) {
                        forward = !forward;
                    }
                }
                else {
                    recyclerCustom.smoothScrollToPosition(--position);
                    if (position <= 0) {
                        forward = !forward;
                    }
                }

                handler.postDelayed(this, 1500);
            }
        };
        handler.postDelayed(runnable, 1500);
    }

    private void addCustomPhotos() {
        _custom.clear();
        List<MovieInfoModel> movieInfoModelList = new ArrayList();
        movieInfoModelList.add(new MovieInfoModel("Harry Potter", "/hziiv14OpD73u9gAak4XDDfBKa2.jpg"));
        movieInfoModelList.add(new MovieInfoModel("Stranger Things", "/56v2KjBlU4XaOv9rVYEQypROD7P.jpg"));
        movieInfoModelList.add(new MovieInfoModel("Me Before You", "/o4lxNwKJz8oq3R0kLOIsDlHbDhZ.jpg"));
        movieInfoModelList.add(new MovieInfoModel("The How of Us","/z4g41XYVkvgpbR8Q6fa3LmodQfL.jpg"));
        movieInfoModelList.add(new MovieInfoModel("The Croods", "/vg2hf8eAZhULxtQRS87CbrH2WMQ.jpg"));
        for(MovieInfoModel model: movieInfoModelList){
            _custom.add(model);
        }
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
    @Override
    public void onRefresh() {
        scrollView.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
       // addCustomPhotos();
        forward = true;
        recyclerCustom.smoothScrollToPosition(0);

        this.mPresenter.getNowPlayingMovies();
        this.mPresenter.getUpComingMovies();
        this.mPresenter.getTopRatedMovies();
        this.mPresenter.getPopularMovies();

        swipeRefreshLayout.setRefreshing(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    @Override
    public void onGiveRemark(int movieId) {

    }
}
