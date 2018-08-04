package com.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.butterKnife.KnifeCommand;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * author : taowang
 * date :2018/8/4
 * description:
 **/
public abstract class BaseRxFragment extends RxFragment{
    protected LayoutInflater layoutInflater;
    private View mRootView;
    protected Activity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;

        if (mRootView == null && getLayoutId() > 0) {
            mRootView = inflater.inflate(getLayoutId(), null);
            bindUiCommand(mRootView);
        } else {
            //复用之前的view，ViewPager+Fragment的使用过程
            // ，可以用到避免重复创建view
            ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mRootView);
            }
        }

        return mRootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.mContext = (Activity) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bindEvent();
        initSaveInstanceState(savedInstanceState);
        initData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 使用butternifer绑定控件
     *
     * @param rootView
     */
    public void bindUiCommand(View rootView) {
        KnifeCommand.bind(this);
    }

    /**
     * 是否使用EventBus
     *
     * @return
     */
    public boolean useEventBus() {
        return false;
    }




    /**
     * 初始化bundle数据
     *
     * @param savedInstanceState
     */
    public void initSaveInstanceState(Bundle savedInstanceState) {

    }

    /**
     * 初始化菜单布局
     *
     * @return
     */
    public int getOptionsMenuId() {
        return 0;
    }

    public void bindEvent() {

    }


    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initData();
}
