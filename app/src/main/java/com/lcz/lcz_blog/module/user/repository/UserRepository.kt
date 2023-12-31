package com.liuchuanzheng.lcz_wanandroid.module.home.repository

import com.lcz.lcz_blog.net.common.MainRetrofitManager
import com.liuchuanzheng.lcz_wanandroid.base.BaseRepository

/**
 * @author 刘传政
 * @date 2021/9/15 0015 9:30
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
class UserRepository : BaseRepository() {
    suspend fun login(phone: String, password: String) =
        safeGetData { MainRetrofitManager.apiService.login(phone, password) }

    suspend fun register(phone: String, password: String) =
        safeGetData { MainRetrofitManager.apiService.register(phone, password) }

    suspend fun userInfo() =
        safeGetData { MainRetrofitManager.apiService.userInfo() }

    suspend fun changeIcon(iconUrl: String) =
        safeGetData { MainRetrofitManager.apiService.iconUrl(iconUrl) }

    suspend fun changeUsername(username: String) =
        safeGetData { MainRetrofitManager.apiService.change_username(username) }

}