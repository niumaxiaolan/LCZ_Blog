package com.liuchuanzheng.lcz_wanandroid.base

import com.lcz.lcz_blog.net.common.CODE_FAIL
import com.lcz.lcz_blog.net.common.CommonResultBean
import com.lcz.lcz_blog.util.log.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author 刘传政
 * @date 2021/9/15 0015 9:21
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
abstract class BaseRepository {
    /**
     * 安全的获取数据，调用者不用担心会报错，因为报错后也会返回一个空对象，并且是想要的类型
     * @param workBlock SuspendFunction0<CommonResultBean<T>>
     * @return CommonResultBean<T>
     */
    suspend fun <T> safeGetData(workBlock: suspend () -> CommonResultBean<T>): CommonResultBean<T> {
        return withContext(Dispatchers.IO) {
            try {
                workBlock.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtil.e(e.toString())
                convertExceptionToBean<T>(e)
            } finally {

            }
        }

    }

    fun <T> convertExceptionToBean(e: Exception): CommonResultBean<T> {
        var exceptionResponse: CommonResultBean<T> = CommonResultBean<T>()
        exceptionResponse.code = CODE_FAIL
        exceptionResponse.msg = e.message ?: "网络不好!请稍后重试。"
        exceptionResponse.data = null
        return exceptionResponse
    }
}