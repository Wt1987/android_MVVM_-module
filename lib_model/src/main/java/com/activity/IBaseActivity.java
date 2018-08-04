package com.activity;

/**
 * author : taowang
 * date :2018/7/31
 * description:
 **/
public interface IBaseActivity {

    /**
     * 初始化界面传递参数
     */
    void initParam();
    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();

}
