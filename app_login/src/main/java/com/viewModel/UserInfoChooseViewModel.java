package com.viewModel;


import android.content.Context;
import com.http.util.HttpDirector;
import com.serviceImpl.LoginHttpApiService;

/**
 * author : taowang
 * date :2018/8/3
 * description:
 **/
public class UserInfoChooseViewModel extends BaseViewModel {

    LoginHttpApiService mLoginHttpApiService;

    public UserInfoChooseViewModel(Context context) {
        super(context);
        mLoginHttpApiService = HttpDirector.getInstance().createReq(LoginHttpApiService.class);
    }



}
