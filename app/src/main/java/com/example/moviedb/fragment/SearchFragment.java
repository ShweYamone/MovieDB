package com.example.moviedb.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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

    @BindView(R.id.recycler_search_movie)
    RecyclerView recyclerSearchMovie;

    private MovieAdapter mAdapter;

    private ServiceHelper.ApiService mService;

    private SearchPresenterImpl mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private MyanProgressDialog mDialog;

    private int page = 1;

    private String mQuery;

    //final int DRAWABLE_LEFT = 0;
    // final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    // final int DRAWABLE_BOTTOM = 3;


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

        Drawable drawableRight = etSearch.getCompoundDrawables()[DRAWABLE_RIGHT];
        drawableRight.setTint(getResources().getColor(R.color.transparent));



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
                    drawableRight.setTint(getResources().getColor(R.color.color_cancel));
                    mQuery = s.toString();
                    mAdapter.clear();
                    mPresenter.getMoviesByTitle(mQuery);
                }
                else {
                    drawableRight.setTint(getResources().getColor(R.color.transparent));
                    cvDataError.setVisibility(View.GONE);
                    mAdapter.clear();
                }

            }
        });

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //final int DRAWABLE_LEFT = 0;
                // final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                // final int DRAWABLE_BOTTOM = 3;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etSearch.getRight() - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        etSearch.setText("");
                        mAdapter.clear();

                        return true;
                    }
                }
                return false;
            }


        });


        mPresenter.onAttachView(this);
        mPresenter.onUIReady();

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
