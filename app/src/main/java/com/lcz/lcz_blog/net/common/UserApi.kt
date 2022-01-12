package com.lcz.lcz_blog.net.common

import com.lcz.lcz_blog.module.user.bean.LoginResult
import com.lcz.lcz_blog.net.common.CommonResultBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST("/user/login")
    suspend fun login(@Query("phone") phone: String, @Query("password") password: String): CommonResultBean<LoginResult>

}