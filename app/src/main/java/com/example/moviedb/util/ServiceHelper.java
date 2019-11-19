package com.example.moviedb.util;

import android.content.Context;


import com.example.moviedb.model.GetVideoResultModel;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.model.WatchListBody;
import com.example.moviedb.model.WatchListModel;

import com.example.moviedb.model.MovieRateListModel;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.moviedb.util.AppConstant.BASE_URL;


/**
 * Created by minthittun on 12/30/2015.
 */
public class ServiceHelper {


    private static ApiService apiService;
    private static Cache cache;

    public static ApiService getClient(final Context context) {
        if (apiService == null) {

            Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    int maxAge = 300; // read from cache for 5 minute
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                }
            };

            //setup cache
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            cache = new Cache(httpCacheDirectory, cacheSize);

            final OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
            okClientBuilder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            // Add other Interceptors
            okClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
//                            .header("Authorization", token_type + " " + access_token)
                            .method(original.method(), original.body())
                            .build();


                    Response response =  chain.proceed(request);

                    if (response.code() == 401){
                        // Magic is here ( Handle the error as your way )
                        return response;
                    }
                    return response;
                }
            });
            okClientBuilder.cache(cache);

            OkHttpClient okClient = okClientBuilder.build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = client.create(ApiService.class);
        }
        return apiService;
    }

    public static void removeFromCache(String url) {
        try {
            Iterator<String> it = cache.urls();
            while (it.hasNext()) {
                String next = it.next();
                if (next.contains(BASE_URL + url)) {
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface ApiService {

//        @POST("mobileUser/sendLoginData")
//        Call<MemberModel> sendLoginData(@Body MemberModel model);
//
//        @GET("mobileUser/getMemberData")
//        Call<MemberModel> getUserInfo(@Query("UserAppID") String UserAppID);
/*
        @GET("authentication/token/new")
        Observable<ProfileInfoModel> getRequestToken(@Query("api_key") String apiKey);


        @POST("authentication/token/validate_with_login")
        Observable<ProfileInfoModel> getLoginValidate(@Query("api_key") String apiKey,
                                                      @Body LoginRequestBody requestBody);

        @POST("authentication/session/new")
        Observable<ProfileInfoModel> getSession(@Query("api_key") String apiKey,
                                                @Body RequestTokenBody requestTokenBody);


        @GET("movie/{movie_id}")
        Observable<MovieDetailModel> getMovieDetail(@Path("movie_id") int id, @Query("api_key") String apiKey);
*/
        @GET("movie/now_playing")
        Observable<MovieListModel> getNowShowingMovies(@Query("api_key") String apiKey,
                                                       @Query("language") String language,
                                                       @Query("page") int page);

        @GET("movie/top_rated")
        Observable<MovieListModel> getTopRatedMovies(@Query("api_key") String apiKey,
                                                    @Query("language") String language,
                                                    @Query("page") int page);

        @GET("movie/upcoming")
        Observable<MovieListModel> getUpComingMovies(@Query("api_key") String apiKey,
                                                     @Query("language") String language,
                                                     @Query("page") int page);

        @GET("movie/popular")
        Observable<MovieListModel> getPopularMovies(@Query("api_key") String apiKye,
                                                    @Query("language") String language,
                                                    @Query("page") int page);

        @GET("search/multi")
        Observable<MovieListModel> getMoviesByTitle(@Query("api_key") String apiKey,
                                                    @Query("language") String language,
                                                    @Query("query") String query,
                                                    @Query("page") int page);

        @GET("account/{account_id}/rated/movies")
        Observable<MovieRateListModel> getOwnRatedMovies(@Query("api_key") String apiKey,
                                                         @Query("session_id") String sessionId,
                                                         @Query("page") int page);


        @GET("movie/{movie_id}")
        Observable<MovieInfoModel> getDetails(@Path("movie_id") int movieId,
                                              @Query("api_key") String apiKey);

        @GET("movie/{movie_id}/similar")
        Observable<MovieListModel> getSimilarVideos(@Path("movie_id")int movieId,
                                                    @Query("api_key") String apiKey,
                                                    @Query("page") int page);

        @GET("movie/{movie_id}/videos")
        Observable<GetVideoResultModel> getVideo(@Path("movie_id")int movieId,
                                                 @Query("api_key") String apiKey );



        @GET("movie/{movie_id}/recommendations")
        Observable<MovieListModel> getRecommendedVideos(@Path("movie_id")int movieId,
                                                    @Query("api_key") String apiKey,
                                                    @Query("page") int page);

        @POST("account/{account_id}/watchlist")
        Observable<WatchListModel> addOrRemoveMovieFromWatchList(@Query("api_key") String apiKey,
                                                            @Query("session_id") String sessionId,
                                                            @Body WatchListBody watchListBody);


    }

}
