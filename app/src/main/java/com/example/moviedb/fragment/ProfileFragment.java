package com.example.moviedb.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.activities.LoginActivity;
import com.example.moviedb.activities.MainActivity;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.util.SharePreferenceHelper;

import butterknife.BindView;

public class ProfileFragment extends BaseFragment{

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

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

        if(mSharePreferenceHelper.isLogin()) {
            this.getContext().startActivity(LoginActivity.getMovieDetailActivityIntent(this.getContext()));
        }
        else {
            btnSignIn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(LoginActivity.getMovieDetailActivityIntent(v.getContext()));
                }
            });
        }

    }
}
