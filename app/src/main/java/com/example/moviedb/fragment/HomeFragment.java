package com.example.moviedb.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.moviedb.R;
import com.example.moviedb.adapters.MovieAdapter;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.mvp.presenter.HomePresenterImpl;
import com.example.moviedb.mvp.view.HomeView;
import com.example.moviedb.util.ServiceHelper;

import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeView {



    SmartScrollListener smartScrollListener;

    @BindView(R.id.rv_now_playing)
    RecyclerView recyclerNowPlaying;

    private MovieAdapter mAdapter;

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
        mAdapter = new MovieAdapter();

        mDialog = new MyanProgressDialog(this.getActivity());

        mService = ServiceHelper.getClient(this.getActivity());

        mPresenter = new HomePresenterImpl(new MovieInteractor(mService));
        init();

    }

    private void init() {


        recyclerNowPlaying.setHasFixedSize(true);
        recyclerNowPlaying.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNowPlaying.addItemDecoration(new ItemOffsetDecoration(2));
        recyclerNowPlaying.setAdapter(mAdapter);

        mPresenter.onAttachView(this);
        mPresenter.onUIReady();

    }

    @Override
    public void showUpComingMovieList(List<MovieInfoModel> movieInfoModelList){

    }

    @Override
    public void showPopularMovieList(List<MovieInfoModel> movieInfoModelList){

    }

    @Override
    public void showTopRatedMovieList(List<MovieInfoModel> movieInfoModelList){

    }

    @Override
    public void showNowShowingMovieList(List<MovieInfoModel> movieInfoModelList){
        mAdapter.clear();
        //   mAdapter.showLoading();
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
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
