package com.lcz.lcz_blog.net.common

import com.lcz.lcz_blog.module.blog.bean.AddBlogRequest
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.user.bean.LoginResult
import retrofit2.http.*

interface MainApi {

    @POST("/user/login")
    suspend fun login(@Query("phone") phone: String, @Query("password") password: String): CommonResultBean<LoginResult>

    @GET("/user/user_info")
    suspend fun userInfo(): CommonResultBean<LoginResult>

    @POST("/user/register")
    suspend fun register(@Query("phone") phone: String, @Query("password") password: String): CommonResultBean<*>

    @POST("/user/icon_url")
    suspend fun iconUrl(@Query("iconUrl") iconUrl: String): CommonResultBean<*>

    @POST("/user/change_username")
    suspend fun change_username(@Query("username") username: String): CommonResultBean<*>

    @POST("/blog/query_page")
    suspend fun blogPageList(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): CommonResultBean<BlogPageListResult>

    @POST("/blog/query_title")
    suspend fun searchBlog(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int,
        @Query("keywords") keywords: String
    ): CommonResultBean<BlogPageListResult>

    @POST("/blog/add")
    suspend fun addBlog(@Body request: AddBlogRequest): CommonResultBean<Int>

    @GET("/blog/queryById/{blogId}")
    suspend fun getBlogById(@Path("blogId") blogId: Int): CommonResultBean<BlogPageListResult.Data>

    @POST("/blog/collect")
    suspend fun collect(@Query("blogId") blogId: Int, @Query("type") type: Int): CommonResultBean<*>

}