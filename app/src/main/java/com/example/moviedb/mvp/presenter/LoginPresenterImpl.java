package com.example.moviedb.mvp.presenter;

import android.util.Log;

import com.example.moviedb.R;
import com.example.moviedb.interactor.AccountInteractor;
import com.example.moviedb.interactor.LoginInteractor;
import com.example.moviedb.model.AccountModel;
import com.example.moviedb.model.LoginRequestBody;
import com.example.moviedb.model.ProfileInfoModel;
import com.example.moviedb.model.RequestTokenBody;
import com.example.moviedb.mvp.view.LoginView;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by kaungkhantsoe on 2019-10-18.
 **/
public class LoginPresenterImpl extends BasePresenter implements LoginPresenter {

    private LoginView mView = null;
    private LoginInteractor mInteractor;
    private AccountInteractor accountInteractor;

    public LoginPresenterImpl(LoginInteractor interactor, AccountInteractor accountInteractor) {
        this.mInteractor = interactor;
        this.accountInteractor = accountInteractor;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        this.mView = null;
    }

    @Override
    public void onUIReady() {
        this.mView.checkLogin();
    }

    @Override
    public void onAttachView(LoginView view) {
        this.mView = view;
    }

    @Override
    public void onClickLogin(String username, String password) {
        this.mView.showLoading();

        if (username.length() == 0) {
            this.mView.showDialogMsg(mView.context().getResources().getString(R.string.error), mView.context().getResources().getString(R.string.please_fill_in_username));
        } else if (password.length() == 0) {
            this.mView.showDialogMsg(mView.context().getResources().getString(R.string.error), mView.context().getResources().getString(R.string.please_fill_in_password));
        } else {

            getRequestToken(username, password);
        }
    }

    private void getRequestToken(String username, String password) {
        ServiceHelper.removeFromCache("authentication/token/new");

        this.mInteractor.getRequestToken().subscribe(new Observer<ProfileInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {

                addDisposableOberver(d);
            }

            @Override
            public void onNext(ProfileInfoModel profileInfoModel) {
                Log.i("Status_code111", profileInfoModel.getRequest_token());
                if (profileInfoModel != null) {
                    if (profileInfoModel.isFailure() || profileInfoModel.getRequest_token().length() == 0) {

                        mView.showDialogMsg(mView.context().getResources().getString(R.string.error),
                                mView.context().getResources().getString(R.string.error_retrieving_data));

                    }  else {
                        Log.i("Status_code111", "toValidateLogin");
                        validateLogin(username,password,profileInfoModel.getRequest_token());
                    }
                } else {
                    mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                            mView.context().getResources().getString(R.string.please_check_your_internet_connection));
                }
            }

            @Override
            public void onError(Throwable e) {

                mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                        e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void validateLogin(String username, String password, String requestToken) {

        Log.i("Status_code", username + " " + password + " " + requestToken);

        this.mInteractor.getLoginValidate(new LoginRequestBody(username,password,requestToken)).subscribe(new Observer<ProfileInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposableOberver(d);
            }

            @Override
            public void onNext(ProfileInfoModel profileInfoModel) {

                if (profileInfoModel != null) {

                    if ( profileInfoModel.getStatus_code() == 30) {


                        mView.showDialogMsg(mView.context().getResources().getString(R.string.error),
                               mView.context().getResources().getString(R.string.incorrect_user_name_or_password));
                        mView.showDialogMsg("Error","getStatus_code=30");
                    } else  if (profileInfoModel.isFailure()) {
                        mView.showDialogMsg(mView.context().getResources().getString(R.string.error),
                                mView.context().getResources().getString(R.string.error_retrieving_data));
                    } else {
                        getSessionId(requestToken);
                    }

                } else {

                    mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                            mView.context().getResources().getString(R.string.please_check_your_internet_connection));
                }

            }

            @Override
            public void onError(Throwable e) {


                Log.i("Status_error", "error");
                Log.i("Status_error", "error");
                HttpException httpException = (HttpException) e;

                if(httpException.code() == 401) {

                    mView.showDialogMsg(mView.context().getResources().getString(R.string.error),
                            mView.context().getResources().getString(R.string.incorrect_user_name_or_password));

                    Log.i("Status_error", httpException.getMessage());
                    mView.showDialogMsg(mView.context().getResources().getString(R.string.error),
                            mView.context().getResources().getString(R.string.incorrect_user_name_or_password));
                } else {
                    mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                            e.getLocalizedMessage());
                }


            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getSessionId(String requestToken) {

        this.mInteractor.getSession(new RequestTokenBody(requestToken)).subscribe(new Observer<ProfileInfoModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposableOberver(d);
            }

            @Override
            public void onNext(ProfileInfoModel profileInfoModel) {
                if (profileInfoModel != null) {

                    if (profileInfoModel.isFailure()) {
                        mView.showDialogMsg(mView.context().getResources().getString(R.string.error),
                                mView.context().getResources().getString(R.string.error_retrieving_data));
                    } else {
                        getAccount(profileInfoModel.getSession_id());
                        mView.saveLoginData(profileInfoModel.getSession_id());
                    }

                }else {

                    mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                            mView.context().getResources().getString(R.string.please_check_your_internet_connection));
                }
            }

            @Override
            public void onError(Throwable e) {

                mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                        e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                mView.onLoginComplete();
                mView.showToastMsg("login success");
            }
        });
    }

    public void getAccount(String sessionId) {
        this.accountInteractor.getAccoount(sessionId)
                .subscribe(new Observer<AccountModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(AccountModel accountModel) {
                        mView.setUserName_ID(accountModel.getName(), accountModel.getId());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
