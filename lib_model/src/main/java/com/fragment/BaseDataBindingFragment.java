package com.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.butterKnife.KnifeCommand;
import com.viewModel.BaseViewModel;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * author : taowang
 * date :2018/7/31
 * description:
 **/
public abstract class BaseDataBindingFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment
        implements IBaseFragment {
    protected V binding;
    protected VM viewModel;
    protected View contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.removeRxBus();
        viewModel.onDestroy();
        viewModel = null;
        binding.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (initVariableId() > 0) {
            binding = DataBindingUtil.inflate(inflater, initContentView(), container, false);
            binding.setVariable(initVariableId(), viewModel = initViewModel());
            bindUiCommand(binding.getRoot());
        } else {
            if (contentView == null && initContentView() > 0) {
                contentView = inflater.inflate(initContentView(), null);
                bindUiCommand(contentView);
            } else {
                //复用之前的view，ViewPager+Fragment的使用过程
                // ，可以用到避免重复创建view
                ViewGroup viewGroup = (ViewGroup) contentView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(contentView);
                }
            }
            return contentView;
        }
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        initViewObservable();

        viewModel.onCreate(savedInstanceState,getActivity() != null ? getActivity().getIntent() : null);

        viewModel.registerRxBus();
    }

    @Override
    public void initParam() {

    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(initVariableId(), viewModel);
        }
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView( );

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public abstract VM initViewModel();

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }


    /**
     * @param rootView
     * 确定使用butternife绑定view
     */
    public void bindUiCommand(View rootView) {
        KnifeCommand.bind(this);
    }

    public boolean onBackPressed() {
        return false;
    }
}