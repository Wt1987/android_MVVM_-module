package com.viewModel;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.activity.LoginMainActivity;
import com.bean.CheckResponse;
import com.binding.command.BindingAction;
import com.binding.command.BindingCommand;
import com.binding.command.BindingConsumer;
import com.duiafudao.app_login.R;
import com.http.rx.DataCallBackListener;
import com.http.rx.RxResultHelper;
import com.http.util.HttpDirector;
import com.serviceImpl.LoginHttpApiService;
import com.util.ui.ToastUtils;

/**
 * author : taowang
 * date :2018/8/3
 * description:
 **/
public class CheckUserInfoViewModel extends BaseViewModel {
    //用户名的绑定
    public ObservableField<String> mPhone = new ObservableField<>("");

    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();

    LoginHttpApiService mLoginHttpApiService ;
    public CheckUserInfoViewModel(Context context) {
        super(context);
        clearBtnVisibility.set(View.INVISIBLE);
        mLoginHttpApiService = HttpDirector.getInstance().createReq(LoginHttpApiService.class);

    }

    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            checkUserInfoRequest();

        }
    });

    public BindingCommand loginOnByWeiXinClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //微信登录


        }
    });

    public BindingCommand loginOnByQQClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //QQ登录

        }
    });

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand
            clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            mPhone.set("");
        }
    });


    //用户名输入框焦点改变的回调事件
    public BindingCommand<String> onTextLongChangeCommand = new BindingCommand<>(new BindingConsumer<String>() {
        @Override
        public void call(String text) {

            if(TextUtils.isEmpty(text)){
                clearBtnVisibility.set(View.INVISIBLE);
            }else {
                clearBtnVisibility.set(View.VISIBLE);
            }

        }
    });

    private void pageToLoginByPwd(){
        Bundle mBundle = new Bundle();
        mBundle.putString("phone",mPhone.get());
        startActivity(LoginMainActivity.class);
    }

    private void checkUserInfoRequest(){

        if (TextUtils.isEmpty(mPhone.get())) {
            ToastUtils.showShort(context.getText(R.string.hint_phone));
            return;
        }

        pageToLoginByPwd();

        //网络请求验证是否注册
        RxResultHelper.getHttpRepose((Activity) context,mLoginHttpApiService.checkUserInfo(mPhone.get()),
                new DataCallBackListener<CheckResponse>(){

                    @Override
                    public void onSuccess(CheckResponse data) {
                        //成功，已经注册，进入登录


                    }

                    @Override
                    public void onError(String code, String msg) {

                        //判断是否是没有注册的错误；
                        //进入注册界面；

                    }
                });
//


    }

}
