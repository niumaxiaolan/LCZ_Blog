package com.dele.kuaiqicha.base.store

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.SPUtils
import com.lcz.lcz_blog.module.mian.activity.MainActivity
import com.lcz.lcz_blog.module.user.activity.LoginActivity
import com.lcz.lcz_blog.store.UserManager
import com.liuchuanzheng.lcz_wanandroid.base.Constant

/**
 * @author 刘传政
 * @date 2019-09-16 17:03
 * QQ:1052374416
 * 电话:18501231486
 * 作用:整个应用的全局设置相关。比如清空一些数据
 * 注意事项:
 */
object AppManager {

    fun analyseGoToMain(activity: Activity) {
        AppManager.checkLoginStatus(activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    /**
     * 判断登录状态,未登录就直接跳转登录页了,登录了就直接执行block
     * @param context Context
     * @param loggedInBlock Function0<Unit>?
     */
    fun checkLoginStatus(activity: Activity, loggedInBlock: ((Context) -> Unit)? = null) {
        if (UserManager.isLogin()) {
            loggedInBlock?.let {
                it(activity)
            }
        } else {
            LoginActivity.startActivity(activity, LoginActivity.Companion.IntentBean())
        }
    }

    fun saveSplashShowedStatus(isFirst: Boolean) {
        SPUtils.getInstance(Constant.SP.AppStatus.spName)
            .put(Constant.SP.AppStatus.Key.splash, isFirst)
    }

    fun getSplashShowedStatus(): Boolean {
        return SPUtils.getInstance(Constant.SP.AppStatus.spName).getBoolean(Constant.SP.AppStatus.Key.splash, false)
    }
}