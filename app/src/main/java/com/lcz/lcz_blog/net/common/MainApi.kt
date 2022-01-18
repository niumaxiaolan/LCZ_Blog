package com.lcz.lcz_blog.net.common

import com.lcz.lcz_blog.module.blog.bean.AddBlogRequest
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.user.bean.LoginResult
import com.lcz.lcz_blog.net.common.CommonResultBean
import io.reactivex.Observable
import retrofit2.http.*

interface MainApi {

    @POST("/user/login")
    suspend fun login(@Query("phone") phone: String, @Query("password") password: String): CommonResultBean<LoginResult>

    @POST("/user/register")
    suspend fun register(@Query("phone") phone: String, @Query("password") password: String): CommonResultBean<*>

    @POST("/blog/query_page")
    suspend fun blogPageList(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): CommonResultBean<BlogPageListResult>

    @POST("/blog/add")
    suspend fun addBlog(@Body request: AddBlogRequest): CommonResultBean<Int>

}