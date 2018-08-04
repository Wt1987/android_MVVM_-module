package com.http.rx;


import android.net.ParseException;
import android.util.MalformedJsonException;

import com.http.exception.ResponseThrowable;
import com.http.exception.SessionException;
import com.http.util.HttpDirector;
import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public abstract class RxResultSubscriber<T> implements Observer<T> {

    private String msg;

    private String code;

    private static final String SYSTEM_ERROR = "-1";

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        if (e instanceof SessionException) {
            SessionException mSessionException = (SessionException) e;
            msg = mSessionException.message;
            code = mSessionException.code;
            //Session过期重新登录
            HttpDirector.getInstance().commandSessionException(false, null);
        } else if (e instanceof ResponseThrowable) {
            ResponseThrowable mResponseThrowable = (ResponseThrowable) e;
            //请求成功，但是，服务器返回了失败
            msg = mResponseThrowable.message;
            code = mResponseThrowable.code;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {

            msg = "解析错误";
            code = SYSTEM_ERROR;
        } else if (e instanceof ConnectException) {

            code = SYSTEM_ERROR;
            msg = "连接失败";
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {

            code = SYSTEM_ERROR;
            msg = "证书验证失败";
        } else if (e instanceof ConnectTimeoutException) {

            code = SYSTEM_ERROR;
            msg = "连接超时";
        } else if (e instanceof java.net.SocketTimeoutException) {

            code = SYSTEM_ERROR;
            msg = "连接超时";
        } else if (e instanceof java.net.UnknownHostException) {

            code = SYSTEM_ERROR;
            msg = "主机地址未知";
        } else {
            code = SYSTEM_ERROR;
            msg = "未知错误";
        }
        _onError(code, msg);

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    public abstract void _onNext(T t);

    public abstract void _onError(String code, String msg);
}
