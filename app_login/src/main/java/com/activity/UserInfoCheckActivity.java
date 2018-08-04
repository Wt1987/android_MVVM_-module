package com.activity;

import android.os.Bundle;

import com.duiafudao.app_login.BR;
import com.duiafudao.app_login.R;
import com.duiafudao.app_login.databinding.ActivityLoginCheckInfoBinding;
import com.viewModel.CheckUserInfoViewModel;

/**
 * author : taowang
 * date :2018/8/3
 * description:检查用户信息
 **/
public class UserInfoCheckActivity extends BaseDataBindingActivity<ActivityLoginCheckInfoBinding,CheckUserInfoViewModel>{
    @Override
    public int initVariableId() {
        return BR.viewLoginModel;
    }

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_login_check_info;
    }

    @Override
    public CheckUserInfoViewModel initViewModel() {
        return new CheckUserInfoViewModel(this);
    }

    @Override
    public void initData() {

    }
}
