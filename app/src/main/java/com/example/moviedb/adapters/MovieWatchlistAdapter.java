package com.example.moviedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.activities.MainActivity;
import com.example.moviedb.activities.MovieDetailActivity;
import com.example.moviedb.common.BaseAdapter;
import com.example.moviedb.model.AccountModel;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.util.SharePreferenceHelper;

import java.security.acl.LastOwnerException;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviedb.util.AppConstant.BASE_IMG_URL;

public class MovieWatchlistAdapter extends BaseAdapter {

    private static final String TAG = "MovieWatchlistAdapter";

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlist_item_movie,parent,false);

        return new MovieWatchlistAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MovieWatchlistAdapter.ViewHolder)holder).bindView((MovieInfoModel)getItemsList().get(position),position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateCustomHeaderViewHolder: " );
        View headerView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_header,parent,false);

        return new MovieWatchlistAdapter.HeaderHolder(headerView);
    }
    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        layoutParams.setFullSpan(true);

        RecyclerHeader recyclerHeader = (RecyclerHeader) getItemsList().get(position);
        ((MovieWatchlistAdapter.HeaderHolder) holder).bindHeader((AccountModel)recyclerHeader.getHeaderData(), position);
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvLetters)
        TextView tvLetters;

        @BindView(R.id.tvUserName)
        TextView tvUserName;

        @BindView(R.id.viewCircle)
        View circleView;

        @BindView(R.id.btnLogOut)
        Button btnLogOut;


        private SharePreferenceHelper mSharePreferenceHelper;
        private Context context;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            mSharePreferenceHelper = new SharePreferenceHelper(context);

        }

        public void bindHeader(AccountModel model, int position) {
            Log.i("bindHeader", "bind");

            //show User Info
            String userName = model.getName();
            changeCircleViewColor(userName.charAt(0));
            String letters = userName.charAt(0) + "";
            int spaceIndex = userName.indexOf(" ");
            if(spaceIndex > 0) {
                letters += userName.charAt(spaceIndex + 1);
            }

            tvUserName.setText(userName);

            tvLetters.setText(letters.toUpperCase());

            btnLogOut.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mSharePreferenceHelper.logoutSharePreference();
                    Intent intent = MainActivity.getMainActivityIntent(v.getContext());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    v.getContext().startActivity(intent);

                }
            });

        }

        private void changeCircleViewColor(char firstLetter) {


            Drawable background = circleView.getBackground();

            int letterInt = firstLetter % 5 + 1;

            switch (letterInt) {

                case 1: background.setTint(context.getResources().getColor(R.color.color_dark_palette1));break;
                case 2: background.setTint(context.getResources().getColor(R.color.color_dark_palette2));break;
                case 3: background.setTint(context.getResources().getColor(R.color.color_dark_palette3));break;
                case 4: background.setTint(context.getResources().getColor(R.color.color_dark_palette4));break;
                case 5: background.setTint(context.getResources().getColor(R.color.color_dark_palette5));break;

            }
        }


    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;


        private MovieInfoModel mMovieModel;

        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this,itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(MovieDetailActivity.getMovieDetailActivityIntent(context,mMovieModel.getId()));
                }
            });
        }

        public void bindView(MovieInfoModel model, int position) {
            this.mMovieModel = model;
            ivMoviePoster.setMinimumHeight(model.getHeight());
            //    ivMoviePoster.setLayoutParams(params);

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

        }
    }
}

