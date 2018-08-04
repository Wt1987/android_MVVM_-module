package com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.butterKnife.KnifeCommand;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * author : taowang
 * date :2018/8/4
 * description:
 **/
public abstract class RxBaseActivity extends RxAppCompatActivity {

    protected Activity mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUiCommand(null);
        }
        initData();
    }

    /**
     * @param rootView
     * 确定使用butternife绑定view
     */
    public void bindUiCommand(View rootView) {
        KnifeCommand.bind(this);
    }
    /**
     * 获取布局id
     *
     * @return
     */
    public abstract int getLayoutId();
    protected abstract void initData();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
