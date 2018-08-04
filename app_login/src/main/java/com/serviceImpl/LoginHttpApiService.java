package com.serviceImpl;

import com.bean.CheckResponse;
import com.bean.LoginResponse;
import com.http.common.bean.BaseResultEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : taowang
 * date :2018/8/3
 * description:
 **/
public interface LoginHttpApiService {


    @POST("api/account/login")
    @FormUrlEncoded
    Observable<BaseResultEntity<CheckResponse>> checkUserInfo(@Field("phone") String phone);

    @POST("api/account/login")
    @FormUrlEncoded
    Observable<BaseResultEntity<LoginResponse>> login(@Field("phone") String phone);

}
