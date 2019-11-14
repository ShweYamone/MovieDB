package com.example.moviedb.mvp.presenter;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by kaungkhantsoe on 2019-10-18.
 **/
public class BasePresenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void onTerminate() {
        dispose();
    }

    void addDisposableOberver(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    private void dispose() {
        if (!compositeDisposable.isDisposed()) {
                compositeDisposable.dispose();
        }
    }

}
