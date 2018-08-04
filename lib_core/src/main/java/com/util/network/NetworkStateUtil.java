/*
 * Copyright (C) 2017 贵阳货车帮科技有限公司
 */

package com.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.util.thirdparty.Preconditions;


/**
 * author : taowang
 * date :2018/7/27
 * description:提供网络类型、连通状态等信息获取
 **/

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class NetworkStateUtil {

    private static final int NETWORK_TYPE_UNKNOWN = 0;
    private static final int NETWORK_TYPE_WIFI = 1;
    private static final int NETWORK_TYPE_2G = 2;
    private static final int NETWORK_TYPE_3G = 3;
    private static final int NETWORK_TYPE_4G = 4;

    public static final String NETWORK_TYPE_NAME_UNKNOWN = "UNKNOWN";
    public static final String NETWORK_TYPE_NAME_WIFI = "Wi-Fi";
    public static final String NETWORK_TYPE_NAME_2G = "2G";
    public static final String NETWORK_TYPE_NAME_3G = "3G";
    public static final String NETWORK_TYPE_NAME_4G = "4G";

    //PLMN(Public Land Mobile Network，公共陆地移动网络 PLMN = MCC + MNC)
    private static final String PLMN_CHINA_MOBILE_TD = "46000";
    private static final String PLMN_CHINA_MOBILE_GSM = "46002";
    private static final String PLMN_CHINA_MOBILE_OTHER = "46007";
    private static final String PLMN_CHINA_UNICOM = "46001";
    private static final String PLMN_CHINA_TELECOM = "46003";

    public static final String PROVIDER_UNKNOWN = "UNKNOWN";
    public static final String PROVIDER_CHINA_MOBILE = "CHINA_MOBILE";
    public static final String PROVIDER_CHINA_UNICOM = "CHINA_UNICOM";
    public static final String PROVIDER_CHINA_TELECOM = "CHINA_TELECOM";

    private NetworkStateUtil() {
        throw new AssertionError("Don't instance! ");
    }

    /**
     * 获取基础网络提供商(移动、联通、电信)
     *
     * @param context A Context of the Application.
     * @return the provider in {@link NetworkStateUtil#PROVIDER_UNKNOWN},{@link NetworkStateUtil#PROVIDER_CHINA_MOBILE},
     * {@link NetworkStateUtil#PROVIDER_CHINA_UNICOM}, {@link NetworkStateUtil#PROVIDER_CHINA_TELECOM}
     * @throws NullPointerException if {@code context} is null.
     */
    public static String getProvider(Context context) {
        Preconditions.checkNotNull(context, "must not be null");
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (TelephonyManager.SIM_STATE_READY == telephonyManager.getSimState()) {
            String operator = telephonyManager.getNetworkOperator();
            if (operator != null) {
                switch (operator) {
                    case PLMN_CHINA_MOBILE_TD:
                    case PLMN_CHINA_MOBILE_GSM:
                    case PLMN_CHINA_MOBILE_OTHER:
                        return PROVIDER_CHINA_MOBILE;
                    case PLMN_CHINA_UNICOM:
                        return PROVIDER_CHINA_UNICOM;
                    case PLMN_CHINA_TELECOM:
                        return PROVIDER_CHINA_TELECOM;
                    default:
                        return PROVIDER_CHINA_TELECOM;
                }
            }
        }
        return PROVIDER_UNKNOWN;
    }

    /**
     * 获取当前网络类型
     *
     * @param context A Context of the Application.
     * @return the network type in {@link NetworkStateUtil#NETWORK_TYPE_NAME_2G},{@link NetworkStateUtil#NETWORK_TYPE_NAME_3G},
     * {@link NetworkStateUtil#NETWORK_TYPE_NAME_4G}, {@link NetworkStateUtil#NETWORK_TYPE_NAME_WIFI},
     * {@link NetworkStateUtil#NETWORK_TYPE_NAME_UNKNOWN}
     * @throws NullPointerException if {@code context} is null.
     */
    public static String getCurrentNetworkTypeName(Context context) {
        Preconditions.checkNotNull(context, "must not be null");
        switch (getNetworkType(context)) {
            case NETWORK_TYPE_WIFI:
                return NETWORK_TYPE_NAME_WIFI;
            case NETWORK_TYPE_2G:
                return NETWORK_TYPE_NAME_2G;
            case NETWORK_TYPE_3G:
                return NETWORK_TYPE_NAME_3G;
            case NETWORK_TYPE_4G:
                return NETWORK_TYPE_NAME_4G;
            case NETWORK_TYPE_UNKNOWN:
                return NETWORK_TYPE_NAME_UNKNOWN;
            default:
                return NETWORK_TYPE_NAME_UNKNOWN;
        }
    }

    private static int getNetworkType(Context context) {

        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return NETWORK_TYPE_UNKNOWN;
        }
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return NETWORK_TYPE_WIFI;
        }
        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            switch (networkInfo.getSubtype()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return NETWORK_TYPE_2G;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    return NETWORK_TYPE_3G;
                case TelephonyManager.NETWORK_TYPE_LTE:
                case TelephonyManager.NETWORK_TYPE_IWLAN:
                    return NETWORK_TYPE_4G;
                default:
                    return NETWORK_TYPE_UNKNOWN;
            }
        }

        return NETWORK_TYPE_UNKNOWN;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    /**
     * 网络是否已连接(不一定能访问internet)
     *
     * @param context A Context of the Application.
     * @return {@code true} if network connectivity exists, {@code false} otherwise.
     * @throws NullPointerException if {@code context} is null.
     */
    public static boolean isConnected(Context context) {
        Preconditions.checkNotNull(context, "must not be null");
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 移动网络是否连接
     *
     * @param context A Context of the Application.
     * @return {@code true} if the mobile network available, {@code false} otherwise.
     * @throws NullPointerException if {@code context} is null.
     */
    public static boolean isMobileNetworkConnected(Context context) {
        Preconditions.checkNotNull(context, "must not be null");
        int networkType = getNetworkType(context);
        return NETWORK_TYPE_2G == networkType
                || NETWORK_TYPE_3G == networkType
                || NETWORK_TYPE_4G == networkType;
    }

    /**
     * 4G是否连接
     *
     * @param context A Context of the Application.
     * @return {@code true} if the 4G available, {@code false} otherwise.
     * @throws NullPointerException if {@code context} is null.
     */
    public static boolean is4GConnected(Context context) {
        Preconditions.checkNotNull(context, "must not be null");
        return NETWORK_TYPE_4G == getNetworkType(context);
    }

    /**
     * wifi是否连接
     *
     * @param context A Context of the Application.
     * @return {@code true} if the wifi available, {@code false} otherwise.
     * @throws NullPointerException if {@code context} is null.
     */
    public static boolean isWifiConnected(Context context) {
        Preconditions.checkNotNull(context, "must not be null");
        return NETWORK_TYPE_WIFI == getNetworkType(context);
    }
}
