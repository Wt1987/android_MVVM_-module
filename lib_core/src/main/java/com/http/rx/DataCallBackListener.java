package com.http.rx;

/**
 * Desc: 数据回调
 * Author: Jooyer
 * Date: 2018-08-02
 * Time: 10:44
 */
public interface DataCallBackListener<T> {
    void onSuccess(T data);

    void onError(String code,String msg);
}
