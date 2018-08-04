/*
 * Copyright (C) 2017 贵阳货车帮科技有限公司
 */

package com.util.text;

import android.support.annotation.Nullable;

/**
 * Created by Zachary on 2017/8/10.
 */

/**
 * General String manipulation utilities.
 * <p>
 * Facilities are provided in the following areas:
 * <ul>
 * <li>Check null or empty-string
 * <li>Check blank-string(defined by {@link Character#isWhitespace(char)})
 * <li>Check string whether contains blank-char or not
 * <li>Delete blank-string contained target string
 * <li>Check string only contains alphabet, numeric, chinese or alphanumeric
 * </ul>
 * <p>
 */
public final class StringUtil {

    public static final String LF = "\n";
    public static final String CR = "\r";
    public static final String EMPTY = "";

    private static final char SBC_WHITESPACE = 12288; //equals "　"
    private static final char DBC_WHITESPACE = 32; //equals " "

    private static final String PATTERN_ALPHABET = "^[a-zA-Z]+$";
    private static final String PATTERN_CHINESE = "^[\\u4E00-\\u9FBB\\uF900-\\uFA2D·]+$";
    private static final String PATTERN_ALPHABET_NUMERIC = "^[a-zA-Z0-9]+$";
    private static final String PATTERN_ALPHABET_NUMERIC_CHINESE = "^[a-z0-9A-Z\\u4E00-\\u9FBB\\uF900-\\uFA2D·]+$";

    private StringUtil() {
        throw new AssertionError("Don't support instantiation");
    }

    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     * <p>
     * <pre>
     * StringUtil.isEmpty(null)      = true
     * StringUtil.isEmpty("")        = true
     * StringUtil.isEmpty(" ")       = false
     * StringUtil.isEmpty("aaa")     = false
     * StringUtil.isEmpty("  aaa  ") = false
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if the CharSequence is empty("") or null
     */
    public static boolean isEmpty(@Nullable final CharSequence content) {
        return content == null || content.length() == 0;
    }

    /**
     * <p>Checks if a CharSequence is not empty ("") and not null.</p>
     * <p>
     * <pre>
     * StringUtil.isNotEmpty(null)      = false
     * StringUtil.isNotEmpty("")        = false
     * StringUtil.isNotEmpty(" ")       = true
     * StringUtil.isNotEmpty("aaa")     = true
     * StringUtil.isNotEmpty("  aaa  ") = true
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if the CharSequence is not empty and not null
     * @see #isEmpty(CharSequence)
     */
    public static boolean isNotEmpty(@Nullable final CharSequence content) {
        return !isEmpty(content);
    }

    /**
     * <p>Checks if a CharSequence is empty (""), null or only whitespace character as defined by {@link
     * Character#isWhitespace(char)}.</p>
     * <p>
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("")        = true
     * <b>StringUtil.isBlank(" ")    = true </b>
     * StringUtil.isBlank("bbb")     = false
     * StringUtil.isBlank("  bbb  ") = false
     * StringUtil.isBlank("  bb b ") = false
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if the CharSequence is null, empty or all whitespace character
     */
    public static boolean isBlank(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return true;
        }
        final int len = content.length();
        for (int i = 0; i < len; i++) {
            if (!(Character.isWhitespace(content.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only as defined by {@link
     * Character#isWhitespace(char)}.</p>
     * <p>
     * <pre>
     * StringUtil.isNotBlank(null)      = false
     * StringUtil.isNotBlank("")        = false
     * <b>StringUtil.isNotBlank(" ")    = false </b>
     * StringUtil.isNotBlank("bbb")     = true
     * StringUtil.isNotBlank("  bbb  ") = true
     * StringUtil.isNotBlank("  bb b ") = true
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if the CharSequence is not empty and not null and not whitespace only
     */
    public static boolean isNotBlank(@Nullable final CharSequence content) {
        return !isBlank(content);
    }

    /**
     * <p>Check whether the given CharSequence contains any whitespace characters as defined by {@link
     * Character#isWhitespace(char)}.</p>
     * <p>
     * <pre>
     * StringUtil.containsWhitespace(null)      = false
     * StringUtil.containsWhitespace("")        = false
     * StringUtil.containsWhitespace("bbb")     = false
     * StringUtil.containsWhitespace("  bbb  ") = true
     * StringUtil.containsWhitespace("  bb b ") = true
     * StringUtil.containsWhitespace("bbbbbb ") = true
     * StringUtil.containsWhitespace("bbb bb ") = true
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if the CharSequence is not empty and contains at least 1 whitespace character
     */
    public static boolean containsWhitespace(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return false;
        }
        final int len = content.length();
        for (int i = 0; i < len; i++) {
            if (Character.isWhitespace(content.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Deletes all whitespaces as defined by {@link Character#isWhitespace(char)} from a String.</p>
     * <p>
     * <pre>
     * StringUtil.deleteWhitespace(null)         = null
     * StringUtil.deleteWhitespace("")           = ""
     * StringUtil.deleteWhitespace("abc")        = "abc"
     * StringUtil.deleteWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param content Need delete whitespace from, may be null
     * @return The string without whitespaces is a new object, null if null-string passed, empty("") if empty("")-string
     * passed.
     */
    @Nullable
    public static CharSequence deleteWhitespace(@Nullable CharSequence content) {
        if (isEmpty(content)) {
            return content;
        }
        final int len = content.length();
        final char[] chs = new char[len];
        int count = 0;
        for (int i = 0; i < len; i++) {
            char ch = content.charAt(i);
            if (!(Character.isWhitespace(ch))) {
                chs[count++] = ch;
            }
        }
        if (count == len) {
            return content;
        }
        return new String(chs, 0, count);
    }

    /**
     * <p>Checks if the CharSequence contains only Unicode letters.</p>
     * <p>
     * <p>Null or an empty CharSequence("") will return false.</p>
     * <p>
     * <pre>
     * StringUtil.isLetter(null)        = false
     * StringUtil.isLetter("")          = false
     * StringUtil.isLetter("  ")        = false
     * StringUtil.isLetter("abc")       = true
     * StringUtil.isLetter("ab2c")      = false
     * StringUtil.isLetter("ab-c")      = false
     * StringUtil.isLetter("今晚打老虎") = true
     * StringUtil.isLetter("αβ")        = true
     * StringUtil.isLetter("αβ今晚打老虎")= true
     * StringUtil.isLetter("ａｄｌｉｕｕ")= true
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if only contains letters, and is non-null and non-empty
     */
    public static boolean isLetter(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return false;
        }
        final int len = content.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isLetter(content.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the CharSequence contains only DBC-case of english-alphabet.</p>
     * <p>
     * <p>Null or an empty CharSequence("") will return false.</p>
     * <p>
     * <pre>
     * StringUtil.isAlphabet(null)          = false
     * StringUtil.isAlphabet("")            = false
     * StringUtil.isAlphabet("  ")          = false
     * StringUtil.isAlphabet("abc")         = true
     * StringUtil.isAlphabet("abcABCc")     = true
     * StringUtil.isAlphabet("ab2c")        = false
     * StringUtil.isAlphabet("ab-c")        = false
     * StringUtil.isAlphabet("今晚打老虎")    = false
     * StringUtil.isAlphabet("αβ")          = false
     * StringUtil.isAlphabet("αβ今晚打老虎")  = false
     * StringUtil.isAlphabet("ｌｋｏａｄｋ")  = false
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if only contains DBC-case of english-alphabet, and is non-null and non-empty
     */
    public static boolean isAlphabet(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return false;
        }
        return content.toString().matches(PATTERN_ALPHABET);
    }

    /**
     * <p>Checks if the CharSequence contains only Unicode letters or digits.</p>
     * <p>
     * <p>Null or an empty CharSequence("") will return false.</p>
     * <p>
     * <pre>
     * StringUtil.isLetterOrNumeric(null)       = false
     * StringUtil.isLetterOrNumeric("")         = false
     * StringUtil.isLetterOrNumeric("  ")       = false
     * StringUtil.isLetterOrNumeric("abc")      = true
     * StringUtil.isLetterOrNumeric("ab c")     = false
     * StringUtil.isLetterOrNumeric("ab2c")     = true
     * StringUtil.isLetterOrNumeric("ab啊")     = true
     * StringUtil.isLetterOrNumeric("ab啊2")    = true
     * StringUtil.isLetterOrNumeric("ab-c")     = false
     * StringUtil.isLetterOrNumeric("ab啊ｃ")   = true
     * StringUtil.isLetterOrNumeric("αβ")       = true
     * StringUtil.isLetterOrNumeric("αβ1")       = true
     * StringUtil.isLetterOrNumeric("αβ啊")      = true
     * StringUtil.isLetterOrNumeric("αβ啊12")    = true
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if only contains letters or digits, and is non-null and non-empty
     */
    public static boolean isLetterOrNumeric(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return false;
        }
        final int len = content.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isLetterOrDigit(content.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if the CharSequence contains only DBC-case of english-alphabet or digits.</p>
     * <p>
     * <p>Null or an empty CharSequence("") will return false.</p>
     * <p>
     * <pre>
     * StringUtil.isAlphabetOrNumeric(null)       = false
     * StringUtil.isAlphabetOrNumeric("")         = false
     * StringUtil.isAlphabetOrNumeric("  ")       = false
     * StringUtil.isAlphabetOrNumeric("abc")      = true
     * StringUtil.isAlphabetOrNumeric("ab c")     = false
     * StringUtil.isAlphabetOrNumeric("ab2c")     = true
     * StringUtil.isAlphabetOrNumeric("ab啊")      = false
     * StringUtil.isAlphabetOrNumeric("ab啊2")     = false
     * StringUtil.isAlphabetOrNumeric("ab-c")     = false
     * StringUtil.isAlphabetOrNumeric("ab啊ｃ")    = false
     * StringUtil.isAlphabetOrNumeric("αβ")       = false
     * StringUtil.isAlphabetOrNumeric("αβ1")      = false
     * StringUtil.isAlphabetOrNumeric("ａ1")      = false
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if only contains DBC-case of english-alphabet or digits, and is non-null and non-empty
     */
    public static boolean isAlphabetOrNumeric(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return false;
        }
        return content.toString().matches(PATTERN_ALPHABET_NUMERIC);
    }

    /**
     * <p>Checks if the CharSequence contains only chinese.</p>
     * <p>
     * <p>Null or an empty CharSequence("") will return false.</p>
     * <p>
     * <pre>
     * StringUtil.isChinese(null)       = false
     * StringUtil.isChinese("")         = false
     * StringUtil.isChinese("  ")       = false
     * StringUtil.isChinese("abc")      = false
     * StringUtil.isChinese("ab c")     = false
     * StringUtil.isChinese("ab2c")     = false
     * StringUtil.isChinese("今晚打老虎") = true
     * StringUtil.isChinese("ab啊2")     = false
     * StringUtil.isChinese("ab-c")     = false
     * StringUtil.isChinese("ab啊ｃ")    = false
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if only contains chinese, and is non-null and non-empty
     */
    public static boolean isChinese(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return false;
        }
        return content.toString().matches(PATTERN_CHINESE);
    }

    /**
     * <p>Checks if the CharSequence contains only DBC-case of english-alphabet or numeric or chinese.</p>
     * <p>
     * <p>Null or an empty CharSequence("") will return false.</p>
     * <p>
     * <pre>
     * StringUtil.isAlphabetNumericOrChinese(null)       = false
     * StringUtil.isAlphabetNumericOrChinese("")         = false
     * StringUtil.isAlphabetNumericOrChinese("  ")       = false
     * StringUtil.isAlphabetNumericOrChinese("abc")      = false
     * StringUtil.isAlphabetNumericOrChinese("ab c")     = false
     * StringUtil.isAlphabetNumericOrChinese("ab2c")     = true
     * StringUtil.isAlphabetNumericOrChinese("今晚打老虎") = true
     * StringUtil.isAlphabetNumericOrChinese("ab啊2")     = true
     * StringUtil.isAlphabetNumericOrChinese("ab2")      = true
     * StringUtil.isAlphabetNumericOrChinese("a啊")      = true
     * StringUtil.isAlphabetNumericOrChinese("啊啊2")    = true
     * StringUtil.isAlphabetNumericOrChinese("ab-c")     = false
     * StringUtil.isAlphabetNumericOrChinese("ab啊ｃ")    = false
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if only contains DBC-case of english-alphabet or numeric or chinese, and is non-null and non-empty
     */
    public static boolean isAlphabetNumericOrChinese(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return false;
        }
        return content.toString().matches(PATTERN_ALPHABET_NUMERIC_CHINESE);
    }

    /**
     * <p>Checks if the CharSequence contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns false.</p>
     * <p>
     * <p>Null or an empty CharSequence("") will return false.</p>
     * <p>
     * <pre>
     * StringUtil.isNumeric(null)                   = false
     * StringUtil.isNumeric("")                     = false
     * StringUtil.isNumeric("  ")                   = false
     * StringUtil.isNumeric("123")                  = true
     * StringUtil.isNumeric("\u0967\u0968\u0969")   = true
     * StringUtil.isNumeric("12 3")                 = false
     * StringUtil.isNumeric("ab2c")                 = false
     * StringUtil.isNumeric("12-3")                 = false
     * StringUtil.isNumeric("12.3")                 = false
     * StringUtil.isNumeric("-123")                 = false
     * StringUtil.isNumeric("+123")                 = false
     * StringUtil.isNumeric("啊啊")                 = false
     * </pre>
     *
     * @param content The CharSequence to check, may be null
     * @return True if only contains digits, and is non-null and non-empty
     */
    public static boolean isNumeric(@Nullable final CharSequence content) {
        if (isEmpty(content)) {
            return false;
        }
        final int len = content.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isDigit(content.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Convert from SBC characters to DBC characters</p>
     *
     * <p>
     * <pre>
     *         StringUtil.sbc2dbc(null)                 = null
     *         StringUtil.sbc2dbc("")                   = ""
     *         StringUtil.sbc2dbc("　")                  = " "
     *         StringUtil.sbc2dbc("啊啊")                = "啊啊"
     *         StringUtil.sbc2dbc("啊啊２")              = "啊啊2"
     *         StringUtil.sbc2dbc("啊啊2")               = "啊啊2"
     *         StringUtil.sbc2dbc("abc")                = "abc"
     *         StringUtil.sbc2dbc("abc2")               = "abc2"
     *         StringUtil.sbc2dbc("abc２")              = "abc2"
     *         StringUtil.sbc2dbc("啊abc")              = "啊abc"
     *         StringUtil.sbc2dbc("啊abc２")             = "啊abc2"
     *         StringUtil.sbc2dbc("！＠＊＊）　ａｂｃ")    = "!@**) abc"
     *         StringUtil.sbc2dbc("啊！＠＊＊）　ａｂｃ")  = "啊!@**) abc"
     *         StringUtil.sbc2dbc("！＠＊＊）　ａｂｃooo") = "!@**) abcooo"
     *     </pre>
     * </p>
     *
     * @param content source string object that maybe contain SBC characters
     * @return DBC string that had been converted
     */
    public static String sbc2dbc(@Nullable final String content) {
        if (isEmpty(content)) {
            return content;
        }
        char[] c = content.toCharArray();
        for (int i = 0; i < c.length; i++) {
            // whitespace ASCII-32
            if (c[i] == SBC_WHITESPACE) {
                c[i] = DBC_WHITESPACE; // equals ' '
                continue;
            }

            // ASCII character 33-126 <-> unicode 65281-65374
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * <p>Convert from DBC characters to SBC characters.</p>
     *
     * * <p>
     * <pre>
     *     StringUtil.dbc2sbc(null)          = null
     *     StringUtil.dbc2sbc("")            = ""
     *     StringUtil.dbc2sbc(" ")           = "　"
     *     StringUtil.dbc2sbc("啊啊")         = "啊啊"
     *     StringUtil.dbc2sbc("啊啊2")        = "啊啊２"
     *     StringUtil.dbc2sbc("啊啊２")        = "啊啊２"
     *     StringUtil.dbc2sbc("abc")          = "ａｂｃ"
     *     StringUtil.dbc2sbc("abc2")         = "ａｂｃ２"
     *     StringUtil.dbc2sbc("啊abc")        = "啊ａｂｃ"
     *     StringUtil.dbc2sbc("啊abc2")       = "啊ａｂｃ２"
     *     StringUtil.dbc2sbc("!@**) abc")    = "！＠＊＊）　ａｂｃ"
     *     StringUtil.dbc2sbc("啊!@**) abc")  = "啊！＠＊＊）　ａｂｃ"
     *     </pre>
     * </p>
     *
     * @param content source string object that maybe contain DBC characters
     * @return SBC string that had been converted
     */
    public static String dbc2sbc(@Nullable final String content) {
        if (isEmpty(content)) {
            return content;
        }

        char[] c = content.toCharArray();
        for (int i = 0; i < c.length; i++) {
            // whitespace ASCII-32
            if (c[i] == DBC_WHITESPACE) {
                c[i] = SBC_WHITESPACE; // equals '　'
                continue;
            }

            // ASCII character 33-126 <-> unicode 65281-65374
            if (c[i] < 127) {
                c[i] = (char) (c[i] + 65248);
            }
        }

        return new String(c);
    }

    /**
     * <p>Checks if all of the CharSequences are empty ("") or null.</p>
     *
     * <pre>
     * StringUtil.isAllEmpty(null)             = true
     * StringUtil.isAllEmpty(null, "")         = true
     * StringUtil.isAllEmpty(new String[] {})  = true
     * StringUtil.isAllEmpty(null, "foo")      = false
     * StringUtil.isAllEmpty(null, "啊x")      = false
     * StringUtil.isAllEmpty("", "bar")        = false
     * StringUtil.isAllEmpty("", "啊x")        = false
     * StringUtil.isAllEmpty("bob", "")        = false
     * StringUtil.isAllEmpty("a啊", "")        = false
     * StringUtil.isAllEmpty("  bob  ", null)  = false
     * StringUtil.isAllEmpty("  啊  ", null)   = false
     * StringUtil.isAllEmpty(" ", "bar")       = false
     * StringUtil.isAllEmpty(" ", "啊")        = false
     * StringUtil.isAllEmpty("foo", "bar")     = false
     * StringUtil.isAllEmpty("啊", "哦")       = false
     * </pre>
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return True if all of the CharSequences are empty or null
     */
    public static boolean isAllEmpty(@Nullable final CharSequence... css) {
        if (css == null || css.length <= 0) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isNotEmpty(cs)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if any of the CharSequences are empty ("") or null.</p>
     *
     * <pre>
     * StringUtil.isAnyEmpty(null)                   = true
     * StringUtil.isAnyEmpty(null, "foo")            = true
     * StringUtil.isAnyEmpty(null, "啊")             = true
     * StringUtil.isAnyEmpty("", "bar")              = true
     * StringUtil.isAnyEmpty("", "啊")               = true
     * StringUtil.isAnyEmpty("bob", "")              = true
     * StringUtil.isAnyEmpty("啊", "")               = true
     * StringUtil.isAnyEmpty("  bob  ", null)        = true
     * StringUtil.isAnyEmpty("  啊  ", null)         = true
     * StringUtil.isAnyEmpty(" ", "bar")             = false
     * StringUtil.isAnyEmpty(" ", "啊")              = false
     * StringUtil.isAnyEmpty("foo", "bar")           = false
     * StringUtil.isAnyEmpty("啊", "哦")              = false
     * StringUtil.isAnyEmpty(new String[]{})         = true
     * StringUtil.isAnyEmpty(new String[]{""})       = true
     * StringUtil.isAnyEmpty(new String[]{"", null}) = true
     * </pre>
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return True if any of the CharSequences are empty or null
     */
    public static boolean isAnyEmpty(@Nullable final CharSequence... css) {
        if (css == null || css.length <= 0) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Checks if none of the CharSequences are empty ("") or null.</p>
     *
     * <pre>
     * StringUtil.isNoneEmpty(null)                   = false
     * StringUtil.isNoneEmpty(null, "foo")            = false
     * StringUtil.isNoneEmpty(null, "哦")             = false
     * StringUtil.isNoneEmpty("", "bar")              = false
     * StringUtil.isNoneEmpty("", "啊")               = false
     * StringUtil.isNoneEmpty("bob", "")              = false
     * StringUtil.isNoneEmpty("啊", "")               = false
     * StringUtil.isNoneEmpty("  bob  ", null)        = false
     * StringUtil.isNoneEmpty("  啊  ", null)         = false
     * StringUtil.isNoneEmpty(new String[] {})        = false
     * StringUtil.isNoneEmpty(new String[]{""})       = false
     * StringUtil.isNoneEmpty(new String[]{"", null}) = false
     * StringUtil.isNoneEmpty(" ", "bar")             = true
     * StringUtil.isNoneEmpty(" ", "啊")              = true
     * StringUtil.isNoneEmpty("foo", "bar")           = true
     * StringUtil.isNoneEmpty("啊", "哦")             = true
     * </pre>
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return True if none of the CharSequences are empty or null
     */
    public static boolean isNoneEmpty(@Nullable final CharSequence... css) {
        return !isAnyEmpty(css);
    }
}
