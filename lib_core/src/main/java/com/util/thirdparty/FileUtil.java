/*
 * Copyright (C) 2017 贵阳货车帮科技有限公司
 */

package com.util.thirdparty;

import android.support.annotation.Nullable;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * General file manipulation utilities.
 * <p>
 * Facilities are provided in the following areas:
 * <ul>
 * <li>convert a human-readable version of the file size(e.g. 124 MB)
 * </ul>
 * <p>
 *
 * @see com.wlqq.utils.io.thirdparty.ApacheFileUtil
 */
public final class FileUtil {

    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a kilobyte.
     */
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf(ONE_KB);

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * The number of bytes in a megabyte.
     */
    public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;

    /**
     * The number of bytes in a gigabyte.
     */
    public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);

    /**
     * The number of bytes in a terabyte.
     */
    public static final long ONE_TB = ONE_KB * ONE_GB;

    /**
     * The number of bytes in a terabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);

    /**
     * The number of bytes in a petabyte.
     */
    public static final long ONE_PB = ONE_KB * ONE_TB;

    /**
     * The number of bytes in a petabyte.
     */
    public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);

    /**
     * The number of bytes in an exabyte.
     */
    public static final long ONE_EB = ONE_KB * ONE_PB;

    /**
     * The number of bytes in an exabyte.
     */
    public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);

    /**
     * The number of bytes in a zettabyte.
     */
    public static final BigInteger ONE_ZB = BigInteger.valueOf(ONE_KB).multiply(BigInteger.valueOf(ONE_EB));

    /**
     * The number of bytes in a yottabyte.
     */
    public static final BigInteger ONE_YB = ONE_KB_BI.multiply(ONE_ZB);

    /**
     * The system file directory separator character.
     */
    public static final char DIR_SEPARATOR = File.separatorChar;

    /**
     * The file extension name separator character
     */
    public static final String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtil() {
        throw new AssertionError("Don't support instantiation!!");
    }

    /**
     * Returns a human-readable version of the file size, where the input represents a specific number of bytes.
     * <p>
     * If the size is over 1GB, the size is returned as the number of whole GB, i.e. the size is rounded down to the
     * nearest GB boundary.
     * </p>
     * <p>
     * Similarly for the 1MB and 1KB boundaries.
     * </p>
     *
     * @param size the number of bytes
     * @return a human-readable display value (includes units - EB, PB, TB, GB, MB, KB or bytes)
     */
    public static String byteCountToDisplaySize(final BigInteger size) { // from apache but modified by our
        BigInteger ret;
        if ((ret = size.divide(ONE_EB_BI))
                .compareTo(BigInteger.ZERO) > 0) {
            return ret  + " EB";
        }
        if ((ret = size.divide(ONE_PB_BI))
                .compareTo(BigInteger.ZERO) > 0) {
            return ret  + " PB";
        }
        if ((ret = size.divide(ONE_TB_BI))
                .compareTo(BigInteger.ZERO) > 0) {
            return ret  + " TB";
        }
        if ((ret = size.divide(ONE_GB_BI))
                .compareTo(BigInteger.ZERO) > 0) {
            return ret  + " GB";
        }
        if ((ret = size.divide(ONE_MB_BI))
                .compareTo(BigInteger.ZERO) > 0) {
            return ret + " MB";
        }
        if ((ret = size.divide(ONE_KB_BI))
                .compareTo(BigInteger.ZERO) > 0) {
            return ret  + " KB";
        }
        return size + " bytes";
    }

    /**
     * Returns a human-readable version of the file size, where the input represents a specific number of bytes.
     * <p>
     * If the size is over 1GB, the size is returned as the number of whole GB, i.e. the size is rounded down to the
     * nearest GB boundary.
     * </p>
     * <p>
     * Similarly for the 1MB and 1KB boundaries.
     * </p>
     *
     * @param size the number of bytes
     * @return a human-readable display value (includes units - EB, PB, TB, GB, MB, KB or bytes)
     */
    public static String byteCountToDisplaySize(final long size) {
        return byteCountToDisplaySize(BigInteger.valueOf(size));
    }

    /**
     * Returns a human-readable version of the file size, where the input represents a specific number of bytes.
     * <p>
     * If the size is over 1GB, the size is returned as the number of decimal point(scale value) GB, i.e. if scale is 2,
     * the value like 1.15GB
     * </p>
     *
     * @param size  the number of bytes
     * @param scale the number of scale (i.e. scale = 4 --> 12345 / 100000 = 0.1235)
     * @return a human-readable display value (includes units - EB, PB, TB, GB, MB, KB or bytes)
     * @throws IllegalArgumentException if scale is less than zero
     */
    public static String byteCountToDisplaySize(final BigDecimal size, int scale) {
        Preconditions.checkArgument(scale >= 0, "Scale must be equal or greater than zero: " + scale);
        BigDecimal ret;
        String unit;
        if ((ret = size.divide(new BigDecimal(ONE_EB_BI)))
                .compareTo(BigDecimal.ONE) >= 0) {
            unit = " EB";
        } else if ((ret = size.divide(new BigDecimal(ONE_PB_BI)))
                .compareTo(BigDecimal.ONE) >= 0) {
            unit = " PB";
        } else if ((ret = size.divide(new BigDecimal(ONE_TB_BI)))
                .compareTo(BigDecimal.ONE) >= 0) {
            unit = " TB";
        } else if ((ret = size.divide(new BigDecimal(ONE_GB_BI)))
                .compareTo(BigDecimal.ONE) >= 0) {
            unit = " GB";
        } else if ((ret = size.divide(new BigDecimal(ONE_MB_BI)))
                .compareTo(BigDecimal.ONE) >= 0) {
            unit = " MB";
        } else if ((ret = size.divide(new BigDecimal(ONE_KB_BI)))
                .compareTo(BigDecimal.ONE) >= 0) {
            unit = " KB";
        } else {
            ret = size;
            unit = " bytes";
        }
        return ret.setScale(scale, BigDecimal.ROUND_HALF_UP) + unit;
    }

    /**
     * Returns a human-readable version of the file size, where the input represents a specific number of bytes.
     * <p>
     * If the size is over 1GB, the size is returned as the number of decimal point(scale value) GB, i.e. if scale is 2,
     * the value like 1.15GB
     * </p>
     *
     * @param size  the number of bytes
     * @param scale the number of scale (i.e. scale = 4 --> 12345 / 100000 = 0.1235)
     * @return a human-readable display value (includes units - EB, PB, TB, GB, MB, KB or bytes)
     * @throws IllegalArgumentException if scale is less than zero
     */
    public static String byteCountToDisplaySize(final long size, int scale) {
        return byteCountToDisplaySize(new BigDecimal(String.valueOf(size)), scale);
    }

    /**
     * Obtain the given fileName's extension name
     * <p>
     * <pre>
     *      getExtensionName(null)               =   null
     *      getExtensionName("")                 =   null
     *      getExtensionName("   ")              =   null
     *      getExtensionName("a.mp3")            =   mp3
     *      getExtensionName("a.b.rmvb")         =   rmvb
     *      getExtensionName("abc")              =   null
     *      getExtensionName(".ssh")             =   ssh
     *      getExtensionName("d:\\test.txt")     =   txt
     *      getExtensionName("d:\\test.txt.")    =   null
     * </pre>
     *
     * @param fileName the given fileName string
     * @return The extension name. Null if fileName is null or don't have extension name
     */
    @Nullable
    public static String getExtensionName(@Nullable String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        int pos = fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        if (pos > -1 && pos < fileName.length() - 1) {
            return fileName.substring(pos + 1);
        }
        return null;
    }
}
