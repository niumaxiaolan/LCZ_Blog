package com.lcz.lcz_blog.store

import android.annotation.SuppressLint
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.liuchuanzheng.lcz_wanandroid.base.Constant

object UserManager {

    /**
     * 清除用户信息
     */
    fun cleanUserInfo() {
        SPUtils.getInstance(Constant.SP.UserInfo.spName).clear()
    }

    /**
     * 更新用户信息
     * 最好是先调用getUserInfo方法，获得UserInfo，再选择性的改变一个属性
     */
    @SuppressLint("RestrictedApi")
    fun updateUserInfo(userInfo: UserInfo?) {
        if (userInfo == null) {
            return
        }
        SPUtils.getInstance(Constant.SP.UserInfo.spName)
            .put(Constant.SP.UserInfo.Key.data, GsonUtils.toJson(userInfo))

    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): UserInfo {
        var userInfo: UserInfo = UserInfo()
        try {
            val json: String =
                SPUtils.getInstance(Constant.SP.UserInfo.spName).getString(Constant.SP.UserInfo.Key.data)
//            userInfo = GsonUtils.fromJson<UserInfo>(json, UserInfo::class.java)
            var gson = Gson()
            var fromJson = gson.fromJson<UserInfo>(json, UserInfo::class.java)
            if (fromJson != null) {
                userInfo = fromJson
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return userInfo
    }

    fun isLogin(): Boolean {
        return getUserInfo().isLogin
    }

    data class UserInfo(
        var isLogin: Boolean = false,
        var icon: String = "",
        var id: Int = -1,
        var username: String = "",
        var phone: String = "",
        var token: String = "", //每次请求需要的token
    )

}