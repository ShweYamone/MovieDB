package com.example.moviedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.moviedb.R;
import com.example.moviedb.common.BaseActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.logo_id)
    ImageView ivLogo;

    private Animation myAnim;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        ivLogo.startAnimation(myAnim);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2700);

    }
}
