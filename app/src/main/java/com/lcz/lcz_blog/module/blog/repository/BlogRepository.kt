package com.lcz.lcz_blog.module.blog.repository

import com.lcz.lcz_blog.module.blog.bean.AddBlogRequest
import com.lcz.lcz_blog.module.blog.bean.EditBlogRequest
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

    suspend fun searchBlog(page: PageBean, keywords: String) =
        safeGetData { MainRetrofitManager.apiService.searchBlog(page.pageNo, page.pageSize, keywords) }

    suspend fun query_collects(page: PageBean) =
        safeGetData { MainRetrofitManager.apiService.query_collects(page.pageNo, page.pageSize) }

    suspend fun query_my_added_blogs(page: PageBean) =
        safeGetData { MainRetrofitManager.apiService.query_my_added_blogs(page.pageNo, page.pageSize) }

    suspend fun register(phone: String, password: String) =
        safeGetData { MainRetrofitManager.apiService.register(phone, password) }

    suspend fun add(request: AddBlogRequest) =
        safeGetData { MainRetrofitManager.apiService.addBlog(request) }

    suspend fun updateBlog(request: EditBlogRequest) =
        safeGetData { MainRetrofitManager.apiService.updateBlog(request) }

    suspend fun deleteBlog(blogId: Int) =
        safeGetData { MainRetrofitManager.apiService.deleteBlog(blogId) }

    suspend fun getById(blogId: Int) =
        safeGetData { MainRetrofitManager.apiService.getBlogById(blogId) }

    suspend fun collect(blogId: Int, type: Int) =
        safeGetData { MainRetrofitManager.apiService.collect(blogId, type) }

}