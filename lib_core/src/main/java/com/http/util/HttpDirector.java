package com.http.util;

import android.content.Context;
import android.text.TextUtils;

import com.http.common.SessionOutCallback;
import com.http.common.intercept.CommonParamIntercept;
import com.http.common.intercept.HeaderIntercept;
import com.http.common.intercept.RetryIntercept;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author : taowang
 * date :2018/7/25
 * description:http请求工具提供者
 **/
public class HttpDirector {


    private String baseUrl;
    private Retrofit retrofit;
    private HttpClientConfiguration config;

    private Context mContext;
    private SessionOutCallback mmSessionOutCallback;

    public static HttpDirector getInstance() {
        return Holder.instance;
    }

    private HttpDirector() {

    }

    private static class Holder {
        private static final HttpDirector instance = new HttpDirector();
    }


    private OkHttpClient getDefaultHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }


    public Retrofit getApiRetrofit() {
        return retrofit;
    }


    /**
     * 初始化全局的retrofit
     * @param config
     * @return
     */
    public Retrofit initRetrofitFormConfig(HttpClientConfiguration config) {

        if(config != null){
            this.baseUrl = config.baseUrl;
            this.mContext = config.context;
            this.mmSessionOutCallback = config.mSessionOutCallback;
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(baseUrl) ? UrlConstants.API_SERVER_URL : baseUrl)
                .client(getHttpClientFromConfig(config))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }


    /**
     * 根据全局的retrofit创建请求
     * @param reqServer
     * @param <T>
     * @return
     */
    public <T> T createReq(Class<T> reqServer){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TextUtils.isEmpty(baseUrl) ? UrlConstants.API_SERVER_URL : baseUrl)
                    .client(getDefaultHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        }
        return retrofit.create(reqServer);
    }


    /**
     * 构造okhttpclient
     * @param config
     * @return
     */
    private OkHttpClient getHttpClientFromConfig(HttpClientConfiguration config) {

        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.addNetworkInterceptor(new StethoInterceptor());
        if(config == null){
            return mBuilder.build();
        }

        if (config.connectTimeout > 0) {
            mBuilder.connectTimeout(config.connectTimeout, TimeUnit.SECONDS);
        }

        if (config.responseTimeout > 0) {
            mBuilder.readTimeout(config.responseTimeout, TimeUnit.SECONDS);
        }

        if (config.responseTimeout > 0) {
            mBuilder.writeTimeout(config.responseTimeout, TimeUnit.SECONDS);
        }

        if (config.retry > 0) {
            mBuilder.addInterceptor(new RetryIntercept(config.retry));
        }

        if (config.listInterceptor != null) {
            for(int i = 0 ; i < config.listInterceptor.size() ; i++){
                mBuilder.addInterceptor(config.listInterceptor.get(i));
            }
        }

        if (config.listNetworkInterceptor != null) {
            for(int i = 0 ; i < config.listNetworkInterceptor.size() ; i++){
                mBuilder.addNetworkInterceptor(config.listNetworkInterceptor.get(i));
            }
        }


        if (config.headers != null) {
            HeaderIntercept mHeaderIntercept = new HeaderIntercept(config.headers);
            mBuilder.addInterceptor(mHeaderIntercept);
        }
        if (config.commonParams != null) {
            CommonParamIntercept mCommonParamIntercept = new CommonParamIntercept(config.commonParams);
            mBuilder.addInterceptor(mCommonParamIntercept);
        }
        if (config.isUseLogger) {
            HttpLoggingInterceptor mHttpLoggingInterceptor = new HttpLoggingInterceptor();
            mHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.addInterceptor(mHttpLoggingInterceptor);
        }

        if (config.maxConnections > 0) {
            OkHttpClient mOkHttpClient = mBuilder.build();
            mOkHttpClient.dispatcher().setMaxRequestsPerHost(config.maxConnections);
            return mOkHttpClient;
        } else {
            return mBuilder.build();
        }


    }

    /**
     * seesion 过期的处理
     * @param isBack
     * @param object
     */
    public void commandSessionException(final boolean isBack,final Object object){

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                if(mmSessionOutCallback != null){
                    if(isBack){
                        //待完善的重新登录操作
                        mmSessionOutCallback.commandBack(object);
                    }else {
                        mmSessionOutCallback.commandUi();
                    }

                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();

    }




    /**
     *  获取一个新的retrofit对象
     * @param config
     * @return
     */
    public Retrofit getNewRetrofitFormConfig(HttpClientConfiguration config,String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TextUtils.isEmpty(baseUrl) ? UrlConstants.API_SERVER_URL : baseUrl)
                .client(getHttpClientFromConfig(config))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();
        return retrofit;
    }


    /**
     * 根据url，创建一个单独的req
     * @param reqServer
     * @param baseUrl
     * @param <T>
     * @return
     */
    public <T> T createNewDefaultReq(Class<T> reqServer , String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getDefaultHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                    .build();
        return retrofit.create(reqServer);
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public SessionOutCallback getMmSessionOutCallback() {
        return mmSessionOutCallback;
    }

    public void setmSessionOutCallback(SessionOutCallback mmSessionOutCallback) {
        this.mmSessionOutCallback = mmSessionOutCallback;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public HttpClientConfiguration getConfig() {
        return config;
    }

    public void setConfig(HttpClientConfiguration config) {
        this.config = config;
    }
}
