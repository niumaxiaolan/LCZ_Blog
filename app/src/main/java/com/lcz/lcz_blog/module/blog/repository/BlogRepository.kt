package com.lcz.lcz_blog.module.blog.repository

import com.lcz.lcz_blog.net.common.MainRetrofitManager
import com.lcz.lcz_blog.util.PageBean
import com.liuchuanzheng.lcz_wanandroid.base.BaseRepository

/**
 * @author 刘传政
 * @date 2021/9/15 0015 9:30
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
class BlogRepository : BaseRepository() {
    suspend fun getPageList(page: PageBean) =
        safeGetData { MainRetrofitManager.apiService.blogPageList(page.pageNo, page.pageSize) }

    suspend fun register(phone: String, password: String) =
        safeGetData { MainRetrofitManager.apiService.register(phone, password) }

}