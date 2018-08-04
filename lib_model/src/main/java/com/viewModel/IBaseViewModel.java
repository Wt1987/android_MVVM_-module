package com.viewModel;


import android.content.Intent;
import android.os.Bundle;

/**
 * author : taowang
 * date :2018/7/21
 * description:
 **/
public interface IBaseViewModel {

    /**
     * View的界面创建时回调
     * INTENT 为上一个界面过来的数据
     */
    void onCreate(Bundle savedInstanceState, Intent mIntent);

    /**
     * View的界面销毁时回调
     */
    void onDestroy();

    /**
     * 注册RxBus
     */
    void registerRxBus();
    /**
     * 移除RxBus
     */
    void removeRxBus();


}
