package com.example.moviedb.fragment;

import android.content.Context;
import android.os.Bundle;

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
import com.example.moviedb.adapters.MovieAdapter;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.common.ItemOffsetDecoration;
import com.example.moviedb.common.SmartScrollListener;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.mvp.presenter.SearchPresenterImpl;
import com.example.moviedb.mvp.view.ProfileView;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;

import butterknife.BindView;

public class ProfileFragment extends BaseFragment implements ProfileView {

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    @BindView(R.id.btnLogOut)
    Button btnLogOut;

    @BindView(R.id.ivUserImage)
    ImageView ivUserImage;

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

    private SearchPresenterImpl mPresenter;

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

        mPresenter = new SearchPresenterImpl(new MovieInteractor(mService));

        if(mSharePreferenceHelper.isLogin()) {
         //   this.getContext().startActivity(LoginActivity.getLoginActivityIntent(this.getContext()));
            layoutToLogin.setVisibility(View.GONE);
            layoutAlreadyLogin.setVisibility(View.VISIBLE);

            Toast.makeText(this.getActivity(), mSharePreferenceHelper.getUserName() ,
                    Toast.LENGTH_SHORT).show();


            tvUserName.setText(mSharePreferenceHelper.getUserName());

            btnLogOut.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mSharePreferenceHelper.logoutSharePreference();
                    v.getContext().startActivity(LoginActivity.getLoginActivityIntent(v.getContext()));

                }
            });





            recyclerViewWatchList.setHasFixedSize(true);
            recyclerViewWatchList.setLayoutManager(new GridLayoutManager(this.getActivity(),3));
            recyclerViewWatchList.addItemDecoration(new ItemOffsetDecoration(2));
            recyclerViewWatchList.setAdapter(mAdapter);
            recyclerViewWatchList.addOnScrollListener(mSmartScrollListener);

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

    @Override
    public void showUserInfo(String userName) {
       // Log.i("!!!", userName);
     //   tvUserName.setText(userName);
    }

    @Override
    public void setUserNameandIDToSharePreference(String userName, int userId){
        mSharePreferenceHelper.setUserName_Id(userName, userId);

        Toast.makeText(this.getActivity(), "Manual" + userName ,
                Toast.LENGTH_SHORT).show();


    }

    @Override
    public void showMyWatchList() {

    }

    @Override
    public Context context() {
        return null;
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
