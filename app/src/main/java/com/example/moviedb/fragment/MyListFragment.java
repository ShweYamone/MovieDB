package com.example.moviedb.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.adapters.MovieAdapter;
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
import com.example.moviedb.util.ServiceHelper;

import java.util.List;

import butterknife.BindView;

public class MyListFragment extends BaseFragment implements RateView {

    @BindView(R.id.recycler_rated_movies)
    RecyclerView recyclerRatedView;

    private RatedMovieAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private RatePresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private int page = 1;

    private String mSession_Id = "b0f14d1104e7fdb867b578bf3331d979d16e4139";


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my_list;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
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
