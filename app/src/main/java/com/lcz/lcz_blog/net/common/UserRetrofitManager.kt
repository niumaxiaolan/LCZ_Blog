package com.lcz.lcz_blog.net.common

import com.lcz.lcz_blog.net.base.RetrofitManager
import com.liuchuanzheng.lcz_wanandroid.base.Constant
import okhttp3.Interceptor

/**
 * @author 刘传政
 * @date 2021/5/25 14:29
 * QQ:1052374416
 * 电话:18501231486
 * 作用:用户网络类
 * 如果有其他host,可以新建一个同样的类管理
 * 注意事项:
 */
object UserRetrofitManager : RetrofitManager<UserApi>() {
    override fun getAppInterceptors(): List<Interceptor> {
        val listOf = listOf<Interceptor>(AppInterceptor())
        return listOf
    }

    override fun getBaseUrl(): String {
        return Constant.Url.request_base_url
    }
}