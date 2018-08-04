package com.viewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.binding.command.BindingAction;
import com.binding.command.BindingCommand;


/**
 * author : taowang
 * date :2018/7/31
 * description:
 **/
public class BaseViewModel implements IBaseViewModel {
    protected Context context;
    protected Fragment fragment;

    public BaseViewModel() {
    }

    public BaseViewModel(Context context) {
        this.context = context;
    }

    public BaseViewModel(Fragment fragment) {
        this(fragment.getContext());
        this.fragment = fragment;
    }



    public void finishActivity(){
        if(context != null && context instanceof Activity){
            ((Activity)context).finish();
        }
    }

    public BindingCommand backCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            finishActivity();
        }
    });
    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    protected void startActivity(Class clz) {
        context.startActivity(new Intent(context, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(context, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }


    @Override
    public void onCreate( Bundle savedInstanceState,Intent mIntent) {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }

}
