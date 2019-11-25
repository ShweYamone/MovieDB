package com.example.moviedb.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.ivSearchCancel)
    ImageView ivSearchCancel;

    @BindView(R.id.recycler_search_movie)
    RecyclerView recyclerSearchMovie;

    private MovieAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private SearchPresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private MyanProgressDialog mDialog;

    private int page = 1;

    private String mQuery;



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
                Log.i("Page:", page+"");
               // Toast.makeText(getContext(), page, Toast.LENGTH_SHORT).show();
                mPresenter.getMoviesByTitleWithPaging(mQuery, page);

            }
        });


        ivSearchCancel.setColorFilter(ContextCompat.getColor(this.getActivity(), R.color.transparent), android.graphics.PorterDuff.Mode.MULTIPLY);

        recyclerSearchMovie.setHasFixedSize(true);
        recyclerSearchMovie.setLayoutManager(new GridLayoutManager(this.getActivity(),3));
        recyclerSearchMovie.addItemDecoration(new ItemOffsetDecoration(2));
        recyclerSearchMovie.setAdapter(mAdapter);
        recyclerSearchMovie.addOnScrollListener(mSmartScrollListener);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length() != 0) {

                    ivSearchCancel.setColorFilter(ContextCompat.getColor(context(), R.color.color_cancel), android.graphics.PorterDuff.Mode.MULTIPLY);

                    mQuery = s.toString();
                    mAdapter.clear();
                    mPresenter.getMoviesByTitle(mQuery);
                }
                else {

                    ivSearchCancel.setColorFilter(ContextCompat.getColor(context(), R.color.transparent), android.graphics.PorterDuff.Mode.MULTIPLY);

                    cvDataError.setVisibility(View.GONE);
                    mAdapter.clear();
                }

            }
        });

        ivSearchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
                mAdapter.clear();
            }
        });

        mPresenter.onAttachView(this);
        mPresenter.onUIReady();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", 1);
    }

    @Override
    public void addMoreMoviesToTheListByQuery(List<MovieInfoModel> movieInfoModelList) {

        Log.i("Page", movieInfoModelList.size()+"");
        for (MovieInfoModel model: movieInfoModelList) {

            mAdapter.add(model);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMovieListByQuery(List<MovieInfoModel> movieInfoModelList) {
        cvDataError.setVisibility(View.GONE);

        page = 1;
        mAdapter.clear();
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
