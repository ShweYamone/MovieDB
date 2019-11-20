package com.example.moviedb.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.activities.LoginActivity;
import com.example.moviedb.adapters.RatedMovieAdapter;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieRateInfoModel;
import com.example.moviedb.mvp.presenter.RatePresenterImpl;
import com.example.moviedb.mvp.view.RateView;
import com.example.moviedb.util.Network;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;

import java.util.List;

import butterknife.BindView;

public class MyRatedListFragment extends BaseFragment implements RateView {

    @BindView(R.id.cv_data_error)
    CardView cvDataError;

    @BindView(R.id.recycler_rated_movies)
    RecyclerView recyclerRatedView;

    @BindView(R.id.layoutNotLoginShow)
    LinearLayout layoutNotLogin;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    private RatedMovieAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private RatePresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private int page = 1;

    private String mSession_Id;

    private SharePreferenceHelper mSharePreferenceHelper;

    private Network mNetwork;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_rated_list;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        mSharePreferenceHelper = new SharePreferenceHelper(context());

        mNetwork = new Network(context());

        if(mNetwork)

        if(mSharePreferenceHelper.isLogin()) {

            layoutNotLogin.setVisibility(View.GONE);
            mSession_Id = mSharePreferenceHelper.getSessionId();

            mAdapter = new RatedMovieAdapter();

            mService = ServiceHelper.getClient(this.getActivity());


            mPresenter = new RatePresenterImpl(new MovieInteractor(mService), mSession_Id);

            mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
                @Override
                public void onListEndReach() {


                    page++;
                    Log.i("Page:", page+"");
                    mPresenter.getOwnRatedMoviesWithPaging(mSession_Id, page);

                }
            });
            recyclerRatedView.setHasFixedSize(true);
            recyclerRatedView.setLayoutManager(new GridLayoutManager(this.getActivity(),1));
            recyclerRatedView.addItemDecoration(new ItemOffsetDecoration(3));
            recyclerRatedView.setAdapter(mAdapter);
            recyclerRatedView.addOnScrollListener(mSmartScrollListener);

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
        cvDataError.setVisibility(View.VISIBLE);

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
