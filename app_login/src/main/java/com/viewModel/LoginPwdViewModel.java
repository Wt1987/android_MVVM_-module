package com.viewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import com.binding.command.BindingAction;
import com.binding.command.BindingCommand;
import com.duiafudao.app_login.R;
import com.http.util.HttpDirector;
import com.serviceImpl.LoginHttpApiService;
import com.util.ui.ToastUtils;

/**
 * author : taowang
 * date :2018/8/3
 * description:密码或者验证码登录
 **/
public class LoginPwdViewModel extends BaseViewModel {


    private String mPhone;
    //密码的绑定
    public ObservableField<String> mPwd = new ObservableField<>("");

    //验证码的绑定
    public ObservableField<String> mCode = new ObservableField<>("");


    //密码清除按钮的显示隐藏绑定
    public ObservableBoolean showBtnVisibility = new ObservableBoolean();

    //显示密码登录布局
    public ObservableBoolean layoutPwdLayoutVisibility = new ObservableBoolean();

    //显示验证码登录布局
    public ObservableBoolean layoutCodeLayoutVisibility = new ObservableBoolean();

    public ObservableField<Drawable> pwdDrawableImg = new ObservableField<>(ContextCompat.getDrawable(context, R.mipmap.show_psw_press));

    LoginHttpApiService mLoginHttpApiService;

    @Override
    public void onCreate(Bundle savedInstanceState, Intent mIntent) {
        super.onCreate(savedInstanceState, mIntent);
        /**
         * 获取上一个页面传过来的参数
         */
        mPhone = mIntent.getStringExtra("phone");

    }


    public LoginPwdViewModel(Context context) {
        super(context);
        layoutPwdLayoutVisibility.set(true);
        layoutCodeLayoutVisibility.set(false);
        showBtnVisibility.set(false);
        mLoginHttpApiService = HttpDirector.getInstance().getApiRetrofit().create(LoginHttpApiService.class);

    }



    public BindingCommand showPwdLayoutOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            layoutPwdLayoutVisibility.set(true);
            layoutCodeLayoutVisibility.set(false);
        }
    });

    public BindingCommand showCodeLayoutOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            layoutPwdLayoutVisibility.set(false);
            layoutCodeLayoutVisibility.set(true);
        }
    });

    public BindingCommand showPwdOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            showBtnVisibility.set(!showBtnVisibility.get());

            if(showBtnVisibility.get()){
                pwdDrawableImg.set(ContextCompat.getDrawable(context, R.mipmap.show_psw));
            }else {
                pwdDrawableImg.set(ContextCompat.getDrawable(context, R.mipmap.show_psw_press));
            }

        }
    });

    public BindingCommand getCodeClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //获取验证码

            ToastUtils.showShort("获取验证码");

        }
    });

    public BindingCommand loginByPwdClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //密码登录
            ToastUtils.showShort("密码登录");
        }
    });
    public BindingCommand forgetPwdClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //忘记密码
            ToastUtils.showShort("忘记密码");
        }
    });

    public BindingCommand loginByCodeClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
           //验证码登录

            ToastUtils.showShort("验证码登录码");

        }
    });




}
