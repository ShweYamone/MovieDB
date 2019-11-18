package com.example.moviedb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.common.BaseAdapter;
import com.example.moviedb.model.MovieRateInfoModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviedb.util.AppConstant.BASE_IMG_URL;

public class RatedMovieAdapter extends BaseAdapter {
    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rated_movie,parent,false);
        return new RatedMovieAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RatedMovieAdapter.ViewHolder)holder).bindView((MovieRateInfoModel)getItemsList().get(position),position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        MovieRateInfoModel mMovieModel;

        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, mMovieModel.getRating(), Toast.LENGTH_SHORT).show();
                    //    context.startActivity(MovieDetailActivity.getMainActivityIntent(context,mMovieModel.getId()));

                }
            });
        }

        public void bindView(MovieRateInfoModel model, int position) {

            this.mMovieModel = model;

            tvTitle.setText(model.getTitle());

            ratingBar.setRating(Float.parseFloat(model.getRating()/2.0+""));


            Glide.with(context)
                    .load(BASE_IMG_URL+model.getPoster_path())
                    .into(ivMoviePoster);

        }
    }
}
