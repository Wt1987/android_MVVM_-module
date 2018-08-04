package com.http.common;

/**
 * author : taowang
 * date :2018/8/2
 * description:seesion过期的处理
 **/
public interface SessionOutCallback {

    void commandBack(Object object);
    void commandUi();

}
