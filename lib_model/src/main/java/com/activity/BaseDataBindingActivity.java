package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.butterKnife.KnifeCommand;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.viewModel.BaseViewModel;

/**
 * author : taowang
 * date :2018/7/31
 * description:数据绑定基础类
 **/
public abstract class BaseDataBindingActivity<V extends ViewDataBinding, VM extends BaseViewModel>
        extends RxAppCompatActivity implements IBaseActivity {

    protected Activity mContext;
    protected V binding;
    protected VM viewModel;
    protected Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mIntent = getIntent();
        initParam();
        initViewDataBinding(savedInstanceState);

        initViewObservable();

        viewModel.onCreate(savedInstanceState,mIntent);
        viewModel.registerRxBus();
        if (getLayoutId(savedInstanceState) > 0) {
            bindUiCommand(null);
        }
        initData();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.removeRxBus();
        viewModel.onDestroy();
        viewModel = null;
        binding.unbind();
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        if (initVariableId() > 0) {
            binding = DataBindingUtil.setContentView(this, getLayoutId(savedInstanceState));
            binding.setVariable(initVariableId(), viewModel = initViewModel());
        } else {
            setContentView(getLayoutId(savedInstanceState));
        }

    }


    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel);
        }
    }


    /**
     * @param rootView 确定使用butternife绑定view
     */
    public void bindUiCommand(View rootView) {
        KnifeCommand.bind(this);
    }


    /**
     * 初始化菜单布局
     *
     * @return
     */
    public int getOptionsMenuId() {
        return 0;
    }


    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();


    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int getLayoutId(Bundle savedInstanceState);

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public abstract VM initViewModel();

    @Override
    public void initParam() {

    }


    @Override
    public void initViewObservable() {

    }
}
