package com.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.butterKnife.KnifeCommand;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public abstract class BaseLazyRxFragment extends BaseLazyFragment {
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
     * @param rootView
     * 确定使用butternife绑定view
     */
    public void bindUiCommand(View rootView) {
        KnifeCommand.bind(this);
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


}
