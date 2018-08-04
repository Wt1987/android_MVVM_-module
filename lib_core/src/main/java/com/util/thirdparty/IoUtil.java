/*
 * Copyright (C) 2017 贵阳货车帮科技有限公司
 */

package com.util.thirdparty;

import android.database.Cursor;
import android.support.annotation.Nullable;

import java.io.Closeable;
import java.io.IOException;


/**
 * General IO stream manipulation utilities.
 * <p>
 * This class provides static utility methods for input/output operations.
 * <ul>
 * <li>closeQuietly - these methods close a stream ignoring nulls and exceptions
 * </ul>
 * <p>
 *
 * @see com.wlqq.utils.io.thirdparty.ApacheIoUtil
 */
public final class IoUtil {

    private IoUtil() {
        throw new AssertionError("Don't instance!!");
    }

    /**
     * Close and release the holding resources that implements {@code Closeable} by invoking the close method!
     *
     * @param closeable The object to close, may be null or already closed
     */
    public static void closeQuietly(final @Nullable Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close and release a collection resources that all implements {@code Closeable} by invoking the close method!
     *
     * @param closeables The objects to close, may be null or already closed
     * @see #closeQuietly(Closeable)
     */
    public static void closeQuietly(@Nullable Closeable... closeables) {
        if (closeables != null && closeables.length > 0) {
            for (Closeable closeable : closeables) {
                closeQuietly(closeable);
            }
        }
    }

    /**
     * Close and release a collection resources that all implements {@code Cursor} by invoking the close method!
     *
     * @param cursor The objects to close, may be null or already closed
     */
    public static void closeQuietly(@Nullable Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }
}