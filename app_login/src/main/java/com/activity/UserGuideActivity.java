package com.activity;

import android.os.Bundle;

import com.duiafudao.app_login.R;
import com.viewModel.UserInfoChooseViewModel;

/**
 * author : taowang
 * date :2018/8/4
 * description:用户引导页面
 **/
public class UserGuideActivity extends BaseDataBindingActivity{
    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_userinfo_choose;
    }

    @Override
    public UserInfoChooseViewModel initViewModel() {
        return new UserInfoChooseViewModel(this);
    }

    @Override
    public void initData() {


    }
}
