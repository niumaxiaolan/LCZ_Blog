package com.liuchuanzheng.lcz_wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcz.lcz_blog.net.common.CommonResultBean
import com.lcz.lcz_blog.util.log.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author 刘传政
 * @date 2021/9/14 0014 18:18
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
abstract class BaseViewModel : ViewModel() {
    /**
     * 创建并执行协程
     * @param workBlock 协程中执行
     * @param errorBlock 错误时执行
     * @return Job
     */
    protected fun launch(
        workBlock: suspend CoroutineScope.() -> Unit,
        errorBlock: ((e: Exception) -> Unit)? = null,
        finillyBlock:(()->Unit)? = null //这里可以发送loading消失的消息
    ): Job {
        return viewModelScope.launch {
            try {
                workBlock()
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtil.e(e.toString())
                errorBlock?.invoke(e)
            } finally {
                finillyBlock?.invoke()
            }
        }
    }

    fun handleResult(
        response: CommonResultBean<*>,
        successBlock: () -> Unit,
        failBlock: () -> Unit
    ) {
        if (response.isServerResultOK()) {
            successBlock()
        } else {
            failBlock()
        }
    }
}