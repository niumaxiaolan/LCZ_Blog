package com.liuchuanzheng.lcz_wanandroid.module.home.repository

import com.lcz.lcz_blog.net.common.UserRetrofitManager
import com.liuchuanzheng.lcz_wanandroid.base.BaseRepository
import retrofit2.http.Query

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
        safeGetData { UserRetrofitManager.apiService.login(phone, password) }

}