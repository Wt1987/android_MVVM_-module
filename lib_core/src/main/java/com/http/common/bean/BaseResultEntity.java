package com.http.common.bean;

/**
 * author : taowang
 * date :2018/7/27
 * description:
 **/
public class BaseResultEntity<T> {
    //判断标示
    public String code;
    //提示信息
    public String description;
    //显示数据（用户需要关心的数据）
    public T resultMap;
    public boolean isOk() {
        return code.equals("1");
    }

}
