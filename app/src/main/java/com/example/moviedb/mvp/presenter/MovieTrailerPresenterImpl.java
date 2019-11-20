package com.example.moviedb.mvp.presenter;

import com.example.moviedb.R;
import com.example.moviedb.interactor.GetVideoResultInteractor;
import com.example.moviedb.model.GetVideoResultModel;
import com.example.moviedb.mvp.view.MovieTrailerView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MovieTrailerPresenterImpl extends  BasePresenter implements MovieTrailerPresenter{
    GetVideoResultInteractor getVideoResultInteractor;
    MovieTrailerView movieTrailerView;

    public MovieTrailerPresenterImpl(GetVideoResultInteractor getVideoResultInteractor) {
        this.getVideoResultInteractor = getVideoResultInteractor;
    }

    @Override
    public void getVideo(int movie_id) {
        movieTrailerView.showLoading();
        this.getVideoResultInteractor.getVideoById(movie_id)
                .subscribe(new Observer<GetVideoResultModel>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(GetVideoResultModel getVideoResultModel) {
                        movieTrailerView.showVideo(getVideoResultModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        movieTrailerView.hideLoading();
                        movieTrailerView.showDialogMsg(movieTrailerView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        movieTrailerView.hideLoading();
                    }
                });
    }

    @Override
    public void onUIReady(int movieId) {
        getVideo(movieId);

    }



    @Override
    public void onAttachView(MovieTrailerView movieTrailerView) {
        this.movieTrailerView=movieTrailerView;
    }
}
