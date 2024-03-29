package com.example.moviedb.common;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected Unbinder unbinder;
    public Context mContext;

    private int someStateValue;
    private final String SOME_VALUE_KEY = "someValueToSave";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.fragment_home, container, false);

        View view = inflater.inflate(getLayoutResource(), container, false);
        unbinder = ButterKnife.bind(this, view);

        mContext=view.getContext();

        if (savedInstanceState != null) {
            someStateValue = savedInstanceState.getInt(SOME_VALUE_KEY);
            // Do something with value if needed
        }

        setUpContents(savedInstanceState);
        return view;
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void setUpContents(Bundle savedInstanceState);



}
