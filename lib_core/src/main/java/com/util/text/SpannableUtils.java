package com.util.text;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

/**
 * author : taowang
 * date :2018/8/3
 * description:Spannable字符串的操作
 **/
public class SpannableUtils {

    private SpannableUtils() {
        throw new AssertionError("Don't instance! ");
    }

    public static SpannableString makeSpannableString(CharacterStyle span, String content) {
        return makeSpannableString(span, content, 0, content.length());
    }

    public static SpannableString makeSpannableString(CharacterStyle span, String content, int start, int end) {
        return makeSpannableString(span, content, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static SpannableString makeSpannableString(CharacterStyle span, String content,
                                                      int start, int end, int flags) {
        SpannableString ss = new SpannableString(content);
        ss.setSpan(span, start, end, flags);
        return ss;
    }

    /**
     * startIndex 从1开始计数
     * 将字符串从start~end的位置设置成相应color
     */
    public static SpannableString setTextColor(SpannableString content, int startIndex, int endIndex, int color) {
        if (TextUtils.isEmpty(content) || startIndex >= endIndex
                || startIndex < 0 || endIndex > content.length()) {
            return null;
        }
        int spanColor = color;
        int alpha = spanColor >> 24;
        if (alpha == 0) {
            spanColor = 0xff000000 | spanColor;
        }
        content.setSpan(new ForegroundColorSpan(spanColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return content;
    }
    /**
     * startIndex 从0开始计数
     * 将字符串从start~end的位置设置成相应color
     */
    public static SpannableString setTextColor(String content, int startIndex, int endIndex, int color) {
        if (TextUtils.isEmpty(content) || startIndex < 0
                || endIndex > content.length() || startIndex > endIndex) {
            return null;
        }
        int spanColor = color;
        int alpha = spanColor >> 24;
        if (alpha == 0) {
            spanColor = 0xff000000 | spanColor;
        }
        SpannableString span = new SpannableString(content);
        span.setSpan(new ForegroundColorSpan(spanColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span;
    }
}
