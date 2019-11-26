package com.example.moviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.activities.MovieDetailActivity;
import com.example.moviedb.common.BaseAdapter;
import com.example.moviedb.model.MovieInfoModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviedb.util.AppConstant.BASE_IMG_URL;

public class MovieAdapter extends BaseAdapter {
    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);

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
    private MovieInfoModel movieInfoModel;
    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;

        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this,itemView);

        }

        public void bindView(MovieInfoModel model, int position) {


            if(model.getPoster_path() == null || model.getPoster_path().equals("")) {
                Glide.with(context)
                        .load(R.drawable.img_placeholder)
                        .into(ivMoviePoster);
            }
            else {
                Glide.with(context)
                        .load(BASE_IMG_URL+model.getPoster_path())
                        .into(ivMoviePoster);
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
