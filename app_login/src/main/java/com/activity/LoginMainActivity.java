package com.activity;

import android.os.Bundle;

import com.duiafudao.app_login.BR;
import com.duiafudao.app_login.R;
import com.duiafudao.app_login.databinding.ActivityLoginCheckPwdBinding;
import com.viewModel.LoginPwdViewModel;

/**
 * author : taowang
 * date :2018/8/3
 * description:检测密码
 **/
public class LoginMainActivity extends BaseDataBindingActivity<ActivityLoginCheckPwdBinding,LoginPwdViewModel>{
    @Override
    public int initVariableId() {
        return BR.viewLoginModel;
    }

    @Override
    public int getLayoutId(Bundle savedInstanceState) {

        return R.layout.activity_login_check_pwd;

    }

    @Override
    public LoginPwdViewModel  initViewModel() {
        return new LoginPwdViewModel (this);
    }

    @Override
    public void initData() {

    }
}
