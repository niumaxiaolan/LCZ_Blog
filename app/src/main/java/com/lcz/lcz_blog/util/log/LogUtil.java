package com.lcz.lcz_blog.util.log;

import android.util.Log;

import androidx.annotation.Nullable;

import com.lcz.lcz_blog.BuildConfig;
import com.lcz.lcz_blog.base.Constant;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目的log统一类.
 * 基本是对logger的包装.方便统一替换三方工具类库
 */
public class LogUtil {
    /**
     * 默认的tag
     */
    public static final String defaultTag = Constant.Tag.tag;
    /**
     * 是否打印log
     */
    public static boolean isShowLog = BuildConfig.DEBUG;
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = -1;
    /**
     * 下面这个变量定义日志级别
     * 这个级别是最低打印级别,相当于logcat的过虑级别
     * 默认所有级别打印
     */
    public static final int MIN_LEVEL = VERBOSE;

    public static void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(defaultTag)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                //是否打印log
                return isShowLog;
            }
        });
    }


    public static void v(String msg) {
        if (MIN_LEVEL <= VERBOSE) {
            Logger.v(msg);
        }

    }

    public static void d(String msg) {
        if (MIN_LEVEL <= DEBUG) {
            Logger.d(msg);
        }
    }

    public static void i(String msg) {
        if (MIN_LEVEL <= INFO) {
            Logger.i(msg);
        }
    }
    public static void i(String tag,String msg) {
        if (MIN_LEVEL <= INFO) {
            Logger.t(tag).i(msg);
        }
    }
    public static void w(String msg) {
        if (MIN_LEVEL <= WARN) {
            Logger.w(msg);
        }
    }

    public static void e(String msg) {
        if (MIN_LEVEL <= ERROR) {
            Logger.e(msg);
        }
    }

    /**
     * 打印成json格式
     *
     * @param jsonStr json字符串.调用者自己保证是json
     */
    public static void json(@Nullable String jsonStr) {
        Logger.json(jsonStr);
    }

    /**
     * 打印方法名
     *
     * @param msg
     */
    public static void m(String msg) {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        if (MIN_LEVEL <= INFO) {
            Logger.i(methodName + ":    " + msg);
        }
    }

    /**
     * 打印方法名
     */
    public static void m() {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        if (MIN_LEVEL <= INFO) {
            Logger.i(methodName);
        }
    }

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final char TOP_LEFT_CORNER = '┌';
    private static final char BOTTOM_LEFT_CORNER = '└';
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final char HORIZONTAL_LINE = '│';
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;

    private static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, TOP_BORDER);
        } else {
            Log.d(tag, BOTTOM_BORDER);
        }
    }

    public static void printJson(String tag, String msg, String headString) {
        if (MIN_LEVEL > DEBUG) {
            return;
        }
        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, HORIZONTAL_LINE + line);
        }
        printLine(tag, false);
    }
}
