package com.lcz.lcz_blog.net.common;


import android.content.Intent;
import android.text.TextUtils;

import com.lcz.lcz_blog.App;
import com.lcz.lcz_blog.module.user.activity.LoginActivity;
import com.lcz.lcz_blog.store.UserManager;

import org.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


/**
 * @author: 刘传政
 * @date: 2019-05-22 13:38
 * QQ:1052374416
 * 作用:统一处理拦截器
 * 比如处理token失效，统一处理某个错误，统一添加某个字段。
 * 注意事项:
 */
public class AppInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String url = original.url().toString();
        Request request;
        Request.Builder builder = chain.request().newBuilder();
        if (!TextUtils.isEmpty(UserManager.INSTANCE.getUserInfo().getToken())) {
            //如果token为空.就不加这个头.否则后台就去校验,最终token失效
            String token = UserManager.INSTANCE.getUserInfo().getToken();
            builder.addHeader("token", token);
        }


        request = builder.build();

        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        if (bodyEncoded(response.headers())) {
            //HTTP (encoded body omitted)
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    //Couldn't decode the response body; charset is likely malformed.
                    return response;
                }
            }

            if (!isPlaintext(buffer)) {
                return response;
            }

            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                //获取到response的body的string字符串
                //do something .... <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                boolean tokenExpired = isTokenExpired(result);
                if (tokenExpired) {
                    UserManager.INSTANCE.cleanUserInfo();
                    Intent intent = new Intent(App.Companion.getInstance(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    App.Companion.getInstance().startActivity(intent);
                }
            }

        }
        return response;
    }

    private boolean isTokenExpired(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            int code = json.optInt("code");
            if (code == CommonResultBeanKt.CODE_TOKEN_EXPIRED) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    static boolean isPlaintext(Buffer buffer) throws EOFException {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}

