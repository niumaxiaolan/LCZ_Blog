package com.lcz.lcz_blog.net.base

import com.lcz.lcz_blog.App
import com.lcz.lcz_blog.util.GenericSuperclassUtil
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * @author 刘传政
 * @date 2021/5/25 14:04
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
abstract class RetrofitManager<API> {
    private val CONNECT_TIMEOUT = 10L
    private val READ_TIMEOUT = 10L
    private val WRITE_TIMEOUT = 10L
    var apiService: API
        get() {
            return field
        }
        private set

    init {
        val cache = Cache(
            File(App.instance.cacheDir, "HttpCache"),
            1024 * 1024 * 100
        )
        var mOkHttpClient: OkHttpClient? = OkHttpClient.Builder().run {
            cache(cache)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            if (!getAppInterceptors().isNullOrEmpty()) {
                getAppInterceptors().forEach {
                    addInterceptor(it)
                }
            }

            addInterceptor(LoggingInterceptor()) //添加自定义log拦截器
            retryOnConnectionFailure(true)//失败重连
            build()
        }

        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
            .build()
        apiService = retrofit.create(getEntityClass()) as API
    }

    /**
     * 设置app自己的拦截器,添加一些请求头,处理token失效等
     */
    abstract fun getAppInterceptors(): List<Interceptor>

    /**
     * 一个网络管理类只有一个baseurl,如果有多个baseurl的项目,就创建多个此类的实现
     */
    abstract fun getBaseUrl(): String

    private fun getEntityClass(): Class<*>? {
        return GenericSuperclassUtil.getActualTypeArgument(this.javaClass,0)
    }

}