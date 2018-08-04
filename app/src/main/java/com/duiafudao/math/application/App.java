package com.duiafudao.math.application;

import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.http.common.SessionOutCallback;
import com.http.util.HttpClientConfiguration;
import com.http.util.HttpDirector;
import com.util.ui.ContextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * create by liumingrui at 2018/7/29
 **/
public class App extends MultiDexApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        initHttp();
        ContextUtils.init(this);
    }

    private void initHttp(){

        Map<String, String> headers = new HashMap<>();
        headers.put("token","123333333333");
        HttpClientConfiguration mConfig = new HttpClientConfiguration.Builder()
                .connectTimeout(30)
                .isUseLoger(true)
                .responseTimeout(30)
                .headers(headers)
                .context(getApplicationContext())
                .baseUrl("http://api.1196u.cn/")
                .retry(8).build();
        HttpDirector.getInstance().initRetrofitFormConfig(mConfig);
        HttpDirector.getInstance().setmSessionOutCallback(new SessionOutCallback() {
            @Override
            public void commandBack(Object object) {

            }

            @Override
            public void commandUi() {

                Toast.makeText(getApplicationContext(),"在这里添加重新登录的逻辑",Toast.LENGTH_LONG).show();

            }
        });
    }



}
