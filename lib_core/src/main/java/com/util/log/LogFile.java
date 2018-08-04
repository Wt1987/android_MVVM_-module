/*
 * Copyright (C) 2016 - 2017 贵阳货车帮科技有限公司
 */

package com.util.log;

import android.os.Environment;


import com.util.thirdparty.IoUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class LogFile {

    public static final String CRLF = "\r\n";
    private static final String LOG_FILE_NAME = "货车帮DebugLog.txt";
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;
    private static final int LOG_FILE_MAX_LENGTH = 20000;
    public static final int RESPONSE_MAX_LENGTH = 1000;

    private static final BlockingQueue<Runnable> WORK_QUEUE =
            new LinkedBlockingQueue<Runnable>();

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "FileLog #" + mCount.getAndIncrement());
        }
    };

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, WORK_QUEUE, THREAD_FACTORY);


    private final File mCacheDir = new File(Environment.getExternalStorageDirectory().getPath());

    private LogFile() {
    }

    @SuppressWarnings("PMD.AccessorClassGeneration")
    public static final class Holder {
        public static final LogFile INSTANCE = new LogFile();
    }

    public static LogFile getInstance() {
        return Holder.INSTANCE;
    }

    public void writeLog(String logTag, String content) {
        writeLog(logTag, content, null);
    }

    public void writeLog(String logTag, Throwable t) {
        writeLog(logTag, null, t);
    }

    public void writeLog(String logTag, String message, Throwable t) {
        StringBuilder sb = new StringBuilder();
        if (message != null) {
            sb.append(message).append(CRLF);
        }
        if (t != null) {
            for (StackTraceElement ste : t.getStackTrace()) {
                sb.append(ste.toString()).append(CRLF);
            }
        }
        if (mCacheDir != null) {
            EXECUTOR.submit(new WriteLogFile(mCacheDir, logTag, sb.toString(), LOG_FILE_MAX_LENGTH));
        }
    }

    public void writeLog(String logTag, String message, File dir, long maxLength) {
        EXECUTOR.submit(new WriteLogFile(dir, logTag, message + CRLF, maxLength));
    }

    private static final class WriteLogFile implements Runnable {
        public File cacheDir;
        public String content;
        public String logTag;
        public long maxLength;

        public WriteLogFile(File cacheDir, String logTag, String content, long maxLength) {
            this.cacheDir = cacheDir;
            this.logTag = logTag;
            this.content = content;
            this.maxLength = maxLength;
        }

        @Override
        public void run() {
            try {
                writeData(cacheDir, content, logTag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void writeData(File cacheDir, String data, String logTag) {
            if (cacheDir != null) {
                File file = new File(cacheDir, generateKey(logTag));
                boolean append = true;
                if (file.length() + data.length() > maxLength) {
                    append = false;
                }

                BufferedWriter bw = null;
                try {
                    OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(file, append), "GBK");
                    bw = new BufferedWriter(fw);
                    bw.write(data);
                    bw.write(CRLF);
                    bw.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    IoUtil.closeQuietly(bw);
                }
            }
        }

        @SuppressWarnings("PMD.UnusedFormalParameter")
        private String generateKey(String logTag) {
            // 这里暂时保留 logTag 参数，如果删除这个参数，LogFile 对外方法也要改动，这会动到 WLHttp 模块
            return LOG_FILE_NAME;
        }
    }

}
