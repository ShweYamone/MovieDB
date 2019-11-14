package com.example.moviedb.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.example.moviedb.R;
import com.example.moviedb.adapters.MovieAdapter;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.mvp.presenter.SearchPresenterImpl;
import com.example.moviedb.mvp.view.SearchView;
import com.example.moviedb.util.ServiceHelper;

import java.util.List;

import butterknife.BindView;

public class SearchFragment extends BaseFragment implements SearchView {

    @BindView(R.id.cv_data_error)
    CardView cvDataError;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.recycler_movie)
    RecyclerView recyclerSearchMovie;

    private MovieAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private SearchPresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private MyanProgressDialog mDialog;

    private int page = 1;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        mAdapter = new MovieAdapter();

        mDialog = new MyanProgressDialog(this.getActivity());

        mService = ServiceHelper.getClient(this.getActivity());

        mPresenter = new SearchPresenterImpl(new MovieInteractor(mService));

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {

                page++;
                mPresenter.getMoviesByTitle();

            }
        });


//        recyclerSearchMovie.setHasFixedSize(true);
//        recyclerSearchMovie.setLayoutManager(new GridLayoutManager(this.getActivity(),2));
//        recyclerSearchMovie.addItemDecoration(new ItemOffsetDecoration(2));
//        recyclerSearchMovie.setAdapter(mAdapter);
//        recyclerSearchMovie.addOnScrollListener(mSmartScrollListener);

       // swipeRefreshLayout.setOnRefreshListener(this);

        mPresenter.onAttachView(this);
        mPresenter.onUIReady();

    }

    @Override
    public void addMoreMoviesToTheList(List<MovieInfoModel> movieInfoModelList) {

    }

    @Override
    public void showMovieList(List<MovieInfoModel> movieInfoModelList) {
        cvDataError.setVisibility(View.GONE);

        page = 1;


        mAdapter.clear();
        //   mAdapter.showLoading();
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
        }
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
