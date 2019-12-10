package com.example.moviedb.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.activities.MovieDetailActivity;
import com.example.moviedb.common.BaseAdapter;
import com.example.moviedb.delegate.MovieDelegate;
import com.example.moviedb.model.MovieInfoModel;

import java.util.Random;

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
            ButterKnife.bind(this, itemView);


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

//            if(position%5==0 | position%5==1 ){
//                Log.e("SPAN","postion in adapter is");
//                itemView.setLayoutParams(new ViewGroup.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
//            }

//            if(position%3==0){
//                DisplayMetrics displaymetrics = new DisplayMetrics();
//                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//                //if you need three fix imageview in width
//                int devicewidth = displaymetrics.widthPixels;
//
//                //if you need 4-5-6 anything fix imageview in height
//                int deviceheight = displaymetrics.heightPixels;
//
//                itemView.getLayoutParams().width = devicewidth;
//
//                //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
//                 itemView.getLayoutParams().height = deviceheight;
//            }



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(MovieDetailActivity.getMovieDetailActivityIntent(context,model.getId()));
                }
            });
        }
    }
}
