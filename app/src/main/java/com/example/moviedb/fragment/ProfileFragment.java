package com.example.moviedb.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedb.R;
import com.example.moviedb.activities.LoginActivity;
import com.example.moviedb.activities.MainActivity;
import com.example.moviedb.adapters.MovieAdapter;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.custom_control.MyanProgressDialog;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieRateInfoModel;
import com.example.moviedb.mvp.presenter.ProfilePresenterImpl;
import com.example.moviedb.mvp.presenter.SearchPresenterImpl;
import com.example.moviedb.mvp.view.ProfileView;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;

import java.util.List;

import butterknife.BindView;

public class ProfileFragment extends BaseFragment implements ProfileView {

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    @BindView(R.id.btnLogOut)
    Button btnLogOut;

    @BindView(R.id.tvLetters)
    TextView tvLetters;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.recycler_watch_list_movie)
    RecyclerView recyclerViewWatchList;

    @BindView(R.id.layoutNotLoginShow)
    LinearLayout layoutToLogin;

    @BindView(R.id.layoutLoginShow)
    LinearLayout layoutAlreadyLogin;

    private MovieAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private ProfilePresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private int page = 1;

    private SharePreferenceHelper mSharePreferenceHelper;


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

        mAdapter = new MovieAdapter();

        mService = ServiceHelper.getClient(this.getActivity());



        if(mSharePreferenceHelper.isLogin()) {

            mPresenter = new ProfilePresenterImpl(new MovieInteractor(mService), mSharePreferenceHelper.getSessionId());

            layoutToLogin.setVisibility(View.GONE);
            layoutAlreadyLogin.setVisibility(View.VISIBLE);

            showUserInfo();


            btnLogOut.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mSharePreferenceHelper.logoutSharePreference();

                    Intent intent = MainActivity.getMainActivityIntent(v.getContext());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    v.getContext().startActivity(MainActivity.getMainActivityIntent(v.getContext()));

                }
            });


            recyclerViewWatchList.setHasFixedSize(true);
            recyclerViewWatchList.setLayoutManager(new GridLayoutManager(this.getActivity(),3));
            recyclerViewWatchList.addItemDecoration(new ItemOffsetDecoration(2));
            recyclerViewWatchList.setAdapter(mAdapter);

         //   recyclerViewWatchList.addOnScrollListener(mSmartScrollListener);

            mPresenter.onAttachView(this);
            mPresenter.onUIReady();



        }
        else {
            layoutAlreadyLogin.setVisibility(View.GONE);
            layoutToLogin.setVisibility(View.VISIBLE);

            btnSignIn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(LoginActivity.getMovieDetailActivityIntent(v.getContext()));
                }
            });
        }

    }

    @Override
    public void showUserInfo() {
        String userName = mSharePreferenceHelper.getUserName();
        String letters = userName.charAt(0) + "";


        int spaceIndex = userName.indexOf(" ");
        if(spaceIndex > 0) {
            letters += userName.charAt(spaceIndex + 1);
        }

        tvUserName.setText(userName);

        tvLetters.setText(letters.toUpperCase());

    }


    @Override
    public void showMyWatchList(List<MovieInfoModel> movieInfoModelList) {
      //  cvDataError.setVisibility(View.GONE);

        page = 1;
        mAdapter.clear();
        for (MovieInfoModel model: movieInfoModelList) {
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
