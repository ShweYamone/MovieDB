package com.example.moviedb.common;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.moviedb.R;
import com.example.moviedb.custom_control.MyanBoldTextView;
import com.google.android.material.appbar.AppBarLayout;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder unbinder;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_text)
    MyanBoldTextView toolbar_text;

    String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);
        setUpContents(savedInstanceState);
    }

    protected void setupToolbar(boolean isChild) {

        if (toolbar != null)
            setSupportActionBar(toolbar);

        if (isChild) {
            if (getSupportActionBar() != null) {

                /*final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
                upArrow.setColorFilter(getResources().getColor(R.color.colorTextColorPrimary), PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);*/


                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         //       toolbar.setNavigationIcon(R.drawable.ic_back);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    protected void setupToolbarText(String text) {
        //getSupportActionBar().setTitle(text);
        toolbar_text.setMyanmarText(text);
    }

    protected void setupToolbarBgColor(String color) {
        toolbar.setBackgroundColor(Color.parseColor(color));
    }

    protected void setupToolbarTextColor(String color) {
        toolbar.setTitleTextColor(Color.parseColor(color));
    }

    protected void setupToolbarTextOnCollapse(String title) {
        mTitle = title;
//        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbarLayout.setTitle("title");
                    setupToolbarText(mTitle);
                    isShow = true;
                } else if(isShow) {
//                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    setupToolbarText(" ");
                    isShow = false;
                }
            }
        });
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void setUpContents(Bundle savedInstanceState);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        forceUpdate();
    }

    // check version on play store and force update
    private void forceUpdate(){
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =  packageManager.getPackageInfo(getPackageName(),0);
            String currentVersion = packageInfo.versionName;

            new ForceUpdateAsync(currentVersion,BaseActivity.this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
