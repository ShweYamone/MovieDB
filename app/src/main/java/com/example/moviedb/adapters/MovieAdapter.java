package com.example.moviedb.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.activities.MovieDetailActivity;
import com.example.moviedb.common.BaseAdapter;
import com.example.moviedb.common.Pageable;
import com.example.moviedb.delegate.MovieDelegate;
import com.example.moviedb.model.MovieInfoModel;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviedb.util.AppConstant.BASE_IMG_URL;

public class MovieAdapter extends BaseAdapter {

    private int SINGLE_VIEW = 12345;
    private int MULTI_VIEW = 23456;

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == SINGLE_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner,parent,false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        }
        return new MovieAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MovieAdapter.ViewHolder)holder).bindView((MovieInfoModel)getItemsList().get(position),position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {

        Pageable item = getItemsList().get(position);

        if (position%4 == 0) {
            return SINGLE_VIEW;
        } else if (item instanceof MovieInfoModel){
            return MULTI_VIEW;
        } else {
            return getItemViewType(position);
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    return type == MULTI_VIEW ? 1 : gridManager.getSpanCount();
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;

        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);


        }

        public void bindView(MovieInfoModel model, int position) {
            if(model.getPoster_path() == null || model.getPoster_path().equals("")) {
                Glide.with(context)
                        .load(R.drawable.img_placeholder)
                        .into(ivMoviePoster);
            }
            else {
                if (position % 4 == 0) {
                    Glide.with(context)
                            .load(BASE_IMG_URL + model.getBackdrop_path())
                            .into(ivMoviePoster);
                }else {
                    Glide.with(context)
                            .load(BASE_IMG_URL+model.getPoster_path())
                            .into(ivMoviePoster);
                }

            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(MovieDetailActivity.getMovieDetailActivityIntent(context,model.getId()));
                }
            });
        }
    }
}
