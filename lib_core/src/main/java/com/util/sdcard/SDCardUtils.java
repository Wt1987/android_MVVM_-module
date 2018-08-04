/*
 * Copyright (C) 2016 - 2017 贵阳货车帮科技有限公司
 */

package com.util.sdcard;

import android.os.Environment;
import android.os.StatFs;


import com.util.thirdparty.ApacheFileUtil;

import java.io.File;
import java.io.IOException;

/**
 * author : taowang
 * date :2018/8/2
 * description:
 **/
public final class SDCardUtils {

    private SDCardUtils() {
        throw new AssertionError("Don't instance! ");
    }

    public static String getSDPath() {
        // 得到当前外部存储设备的目录
        return Environment.getExternalStorageDirectory() + File.separator;
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public static File createSDFile(String fileName) throws IOException {
        if (isSDCardAvailable()) {
            File file = new File(fileName);
            ApacheFileUtil.forceMkdirsParent(file);
            if (file.createNewFile()) {
                return file;
            }
        }
        return null;
    }


    /**
     * 判断手机是否存在SD卡，并是可读写的
     *
     * @return true可读写，false不可用
     */
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void deleteSDCardFolder(File dir) {
        try {
            ApacheFileUtil.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long freeBlocks = sf.getAvailableBlocks();
        //Byte
        return freeBlocks * blockSize;
    }
}
