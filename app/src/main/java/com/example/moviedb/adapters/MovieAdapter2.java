package com.example.moviedb.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.DB.InitializeDatabase;
import com.example.moviedb.R;
import com.example.moviedb.activities.MovieDetailActivity;
import com.example.moviedb.common.BaseAdapter;
import com.example.moviedb.delegate.MovieDelegate;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.RemarkModel;
import com.example.moviedb.util.SharePreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moviedb.util.AppConstant.BASE_IMG_URL;

public class MovieAdapter2 extends BaseAdapter {


    MovieDelegate delegate;
    InitializeDatabase dbHelper;
    SharePreferenceHelper sharePreferenceHelper;
    Boolean isRemark;

    public MovieAdapter2(MovieDelegate delegate) {
        this.delegate = delegate;
    }
    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie2,parent,false);

        return new MovieAdapter2.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MovieAdapter2.ViewHolder)holder).bindView((MovieInfoModel)getItemsList().get(position),position);
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

        @BindView(R.id.iv_bookmark)
        ImageView bookmark;

        @BindView(R.id.ll_cardview)
        LinearLayout cv_item;

        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            dbHelper = InitializeDatabase.getInstance(context);
            sharePreferenceHelper=new SharePreferenceHelper(context);
            ButterKnife.bind(this,itemView);
        }


        public void bindView(MovieInfoModel model, int position) {
            //resize item view of editor car list
            Display display = ((Activity) cv_item.getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            try {
                display.getRealSize(size);
            } catch (NoSuchMethodError err) {
                display.getSize(size);
            }
            int width = size.x;
            int height = size.y;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(width * ( 1.0 / 3.6)),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 10, 0);

            cv_item.setLayoutParams(lp);

            if(!sharePreferenceHelper.isLogin()){
                bookmark.setVisibility(View.GONE);
            }
            isRemark = dbHelper.remarkDAO().getRemarkValue(model.getId(),model.getAccountId());

            if(isRemark) {
                Glide.with(context)
                        .load(R.drawable.ic_bookmark_black_24dp)
                        .into(bookmark);
            }

            if (model.getPoster_path() == null || model.getPoster_path() == "") {
                Glide.with(context)
                        .load(R.drawable.img_placeholder)
                        .into(ivMoviePoster);
            }
            else {
                Glide.with(context)
                        .load(BASE_IMG_URL+model.getPoster_path())
                        .into(ivMoviePoster);
            }
            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isRemark){
                        delegate.onGiveRemark(model.getId());


                        int c = dbHelper.remarkDAO().changeRemarkValue(false,model.getId(),model.getAccountId());
                        isRemark = false;
                        Glide.with(context)
                                .load(R.drawable.ic_bookmark_border_black_24dp)
                                .into(bookmark);
                    }else {
                        isRemark = true;
                        int c = dbHelper.remarkDAO().changeRemarkValue(true,model.getId(),model.getAccountId());
                        if(c==0){
                            dbHelper.remarkDAO().insertRemark(new RemarkModel(model.getId(),model.getAccountId(),true));
                        }
                        Glide.with(context)
                                .load(R.drawable.ic_bookmark_black_24dp)
                                .into(bookmark);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(MovieDetailActivity.getMovieDetailActivityIntent(context,model.getId()));

                }
            });

        }
    }
}
