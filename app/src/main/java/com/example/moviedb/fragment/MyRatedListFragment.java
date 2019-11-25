package com.example.moviedb.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moviedb.DB.InitializeDatabase;
import com.example.moviedb.Entity.Movie;
import com.example.moviedb.R;
import com.example.moviedb.activities.LoginActivity;
import com.example.moviedb.activities.MovieDetailActivity;
import com.example.moviedb.adapters.RatedMovieAdapter;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieRateInfoModel;
import com.example.moviedb.mvp.presenter.RatePresenterImpl;
import com.example.moviedb.mvp.view.RateView;
import com.example.moviedb.util.Network;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyRatedListFragment extends BaseFragment implements RateView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.cv_data_error)
    LinearLayout cvDataError;

    @BindView(R.id.recycler_rated_movies)
    RecyclerView recyclerRatedView;

    @BindView(R.id.layoutNotLoginShow)
    LinearLayout layoutNotLogin;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private RatedMovieAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private RatePresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private MyanProgressDialog mDialog;

    private int page = 1;

    private String mSession_Id;

    private int mAccountId;

    private SharePreferenceHelper mSharePreferenceHelper;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_rated_list;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", 2);
        Log.i("currentchoiceforrate", outState.getInt("curChoice") + "");
    }

    private void init() {
        mSharePreferenceHelper = new SharePreferenceHelper(context());

        if(mSharePreferenceHelper.isLogin()) {

            layoutNotLogin.setVisibility(View.GONE);

            mSession_Id = mSharePreferenceHelper.getSessionId();
            mAccountId = mSharePreferenceHelper.getUserId();

            mAdapter = new RatedMovieAdapter();

            mService = ServiceHelper.getClient(this.getActivity());

            mDialog = new MyanProgressDialog(context());

            mPresenter = new RatePresenterImpl(new MovieInteractor(mService), mSession_Id, mAccountId);

            mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
                @Override
                public void onListEndReach() {


                    page++;
                    Log.i("Page:", page+"");
                    mPresenter.getOwnRatedMoviesWithPaging(mSession_Id, page);

                }
            });

            recyclerRatedView.setHasFixedSize(true);
            recyclerRatedView.addItemDecoration(new ItemOffsetDecoration(3));
            recyclerRatedView.setAdapter(mAdapter);
            recyclerRatedView.addOnScrollListener(mSmartScrollListener);
            swipeRefreshLayout.setOnRefreshListener(this);


            mPresenter.onAttachView(this);
            mPresenter.onUIReady();


        }

       else {
            recyclerRatedView.setVisibility(View.GONE);
            layoutNotLogin.setVisibility(View.VISIBLE);

            btnLogin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(LoginActivity.getLoginActivityIntent(v.getContext()));
                }
            });

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onUIReady();
    }

    @Override
    public void addMoreRatedMoviesToTheList(List<MovieRateInfoModel> movieInfoModelList) {
        Log.i("Page", movieInfoModelList.size()+"");
        for (MovieRateInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showRatedMovieList(List<MovieRateInfoModel> movieInfoModelList) {

        cvDataError.setVisibility(View.GONE);

        page = 1;
        Log.i("movie", movieInfoModelList.size()+"");
        mAdapter.clear();
        for (MovieRateInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
    }

    @Override
    public void resetPageNumberToDefault() {
        page--;
    }

    @Override
    public void showNoRatedMovieInfo() {
        mAdapter.clear();
        Log.i("movie", "NOMOIve");
        cvDataError.setVisibility(View.VISIBLE);

    }

    @Override
    public Context context() {
        return this.getActivity();
    }

    @Override
    public void showLoading() {
        if (!getActivity().isFinishing()) {
            mDialog.showDialog();
        }
    }

    @Override
    public void hideLoading() {
        if (!getActivity().isFinishing()) {
            mDialog.hideDialog();
        }
    }

    @Override
    public void showToastMsg(String msg) {
        this.hideLoading();
        Toast.makeText(context(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialogMsg(String title, String msg) {
        this.hideLoading();
        new AlertDialog.Builder(context())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(getString(R.string.ok), null).show();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        this.mPresenter.getOwnRatedMovies(mAccountId, mSession_Id);
    }
}
