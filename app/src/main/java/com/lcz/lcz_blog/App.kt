package com.lcz.lcz_blog

import android.app.Application
import com.lcz.lcz_blog.util.log.LogUtil
import kotlin.properties.Delegates

/**
 * @author 刘传政
 * @date 2021/9/13 0013 17:20
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
class App :Application() {
    companion object {
        //这写法可以获取时没初始化就报错。实际使用其实意义不大。
        var instance: Application by Delegates.notNull()
            private set

    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        LogUtil.init()
    }
}