package com.example.moviedb.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.moviedb.R;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.custom_control.MyanBoldTextView;
import com.example.moviedb.interactor.AccountInteractor;
import com.example.moviedb.interactor.LoginInteractor;
import com.example.moviedb.mvp.presenter.LoginPresenterImpl;
import com.example.moviedb.mvp.view.LoginView;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_text)
    MyanBoldTextView toolbar_text;

    @BindView(R.id.etUserName)
    EditText etUserName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.ivShowPwd)
    ImageView ivShowPwd;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    private LoginPresenterImpl mPresenter;

    private ServiceHelper.ApiService mService;

    private SharePreferenceHelper mSharePreferenceHelper;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    public static Intent getLoginActivityIntent(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar_text.setMyanmarText("Login");
        init();
    }

    private void init() {
        mService = ServiceHelper.getClient(this);

        mSharePreferenceHelper = new SharePreferenceHelper(this);

        mPresenter = new LoginPresenterImpl(new LoginInteractor(this.mService), new AccountInteractor(this.mService));


        ivShowPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    ivShowPwd.setAlpha(1.0f);

                  //  ivShowPwd.setColorFilter(ContextCompat.getColor(context(), R.color.color_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
                    //Show Password
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    ivShowPwd.setAlpha(0.3f);
                  //  ivShowPwd.setColorFilter(ContextCompat.getColor(context(), R.color.color_edt_grey), android.graphics.PorterDuff.Mode.MULTIPLY);
                    //Hide Password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onClickLogin(etUserName.getText()+"",
                        etPassword.getText()+"");
            }
        });

        mPresenter.onAttachView(this);

        mPresenter.onUIReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onTerminate();
    }


    @Override
    public void saveLoginData(String sessionId) {
      //  Log.e("LOGINACTiVity", "saveLoginData: " + sessionId );
        mSharePreferenceHelper.setLogin(sessionId);
    }

    public void setUserName_ID(String username, int id) {
        mSharePreferenceHelper.setUserName_Id(username, id);
    }

    @Override
    public void onLoginComplete() {

        mSharePreferenceHelper.setUserName_Id(etUserName.getText().toString(), 1);

        this.startActivity(MainActivity.getMainActivityIntent(this));
        this.finish();
    }

    @Override
    public void checkLogin() {
        if (mSharePreferenceHelper.isLogin()) {
            onLoginComplete();
        }
    }

    @Override
    public void showLoading() {

        if (!isFinishing()) {
        //    mDialog.showDialog();
        }
    }

    @Override
    public void hideLoading() {

        if (!isFinishing()) {
         //   mDialog.hideDialog();
        }
    }

    @Override
    public void showToastMsg(String msg) {

        this.hideLoading();
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialogMsg(String title,String msg) {

        this.hideLoading();
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(getString(R.string.ok), null).show();
    }

    @Override
    public Context context() {
        return this;
    }
}
