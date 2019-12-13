package com.example.moviedb.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.moviedb.DB.InitializeDatabase;
import com.example.moviedb.R;
import com.example.moviedb.activities.LoginActivity;
import com.example.moviedb.activities.MainActivity;
import com.example.moviedb.adapters.MovieAdapter;
import com.example.moviedb.adapters.MovieWatchlistAdapter;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.GridItemDecoration;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.AccountModel;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieRateInfoModel;
import com.example.moviedb.mvp.presenter.ProfilePresenterImpl;
import com.example.moviedb.mvp.presenter.SearchPresenterImpl;
import com.example.moviedb.mvp.view.ProfileView;
import com.example.moviedb.util.Network;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class ProfileFragment extends BaseFragment implements ProfileView {

    @BindView(R.id.cv_data_error)
    LinearLayout cvDataError;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    @BindView(R.id.recycler_watch_list_movie)
    RecyclerView recyclerViewWatchList;

    @BindView(R.id.layoutNotLoginShow)
    LinearLayout layoutToLogin;

    @BindView(R.id.layoutLoginShow)
    LinearLayout layoutAlreadyLogin;


    private MovieWatchlistAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private ProfilePresenterImpl mPresenter;

    private int page = 1;

    private String mSession_Id;

    private int mAccountId;

    private SharePreferenceHelper mSharePreferenceHelper;

    private InitializeDatabase dbHelper;
    private static final String TAG = "ProfileFragment";

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init();
    }

    private void init() {
        mSharePreferenceHelper = new SharePreferenceHelper(this.getActivity());

        mSession_Id = mSharePreferenceHelper.getSessionId();

        mAccountId = mSharePreferenceHelper.getUserId();

        mAdapter = new MovieWatchlistAdapter();

        mService = ServiceHelper.getClient(this.getActivity());

        if(mSharePreferenceHelper.isLogin()) {

            dbHelper = InitializeDatabase.getInstance(context());

            mPresenter = new ProfilePresenterImpl(new MovieInteractor(mService), mSession_Id, mAccountId);

            layoutToLogin.setVisibility(View.GONE);
            layoutAlreadyLogin.setVisibility(View.VISIBLE);

            recyclerViewWatchList.setLayoutManager(new StaggeredGridLayoutManager(3,GridLayoutManager.VERTICAL));
            recyclerViewWatchList.addItemDecoration(new GridItemDecoration(0, 3));
            recyclerViewWatchList.setAdapter(mAdapter);

            showMyWatchList(dbHelper.myListDAO().getMyWatchlistMovies(mAccountId));
            Log.e(TAG, "init: " + mAdapter.getItemCount() + "");

        }
        else {
            layoutAlreadyLogin.setVisibility(View.GONE);
            layoutToLogin.setVisibility(View.VISIBLE);

            btnSignIn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(LoginActivity.getLoginActivityIntent(v.getContext()));
                }
            });
        }

    }


    public void showMyWatchList(List<MovieInfoModel> movieInfoModelList) {
        cvDataError.setVisibility(View.GONE);

        page = 1;
        mAdapter.clear();
        Random random = new Random();

        mAdapter.addHeader(new AccountModel(mAccountId, mSharePreferenceHelper.getUserName()));

        for (MovieInfoModel model: movieInfoModelList) {
            int height = random.nextInt(500 - 400 + 1) + 400;
            model.setHeight(height);
            mAdapter.add(model);
        }
    }

    @Override
    public void showMoreWatchList(List<MovieInfoModel> movieInfoModelList) {
        Log.i("Page", movieInfoModelList.size()+"");
        for (MovieInfoModel model: movieInfoModelList) {
            mAdapter.add(model);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void resetPageNumberToDefault() {
        page--;
    }

    @Override
    public void showNoMovieInfo() {
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

    @Override
    public void onResume() {
        super.onResume();
        mSharePreferenceHelper = new SharePreferenceHelper(context());

        if(mSharePreferenceHelper.isLogin()) {

            showMyWatchList(dbHelper.myListDAO().getMyWatchlistMovies(mAccountId));
        }

    }
}
