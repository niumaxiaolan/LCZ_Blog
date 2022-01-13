package com.liuchuanzheng.lcz_wanandroid.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcz.lcz_blog.base.BaseVMEvent
import com.lcz.lcz_blog.base.EventType
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
open class BaseViewModel : ViewModel() {
    //所有通用的事件
    val liveData_event = MutableLiveData<BaseVMEvent>()

    /**
     * 创建并执行协程
     * @param workBlock [@kotlin.ExtensionFunctionType] SuspendFunction1<CoroutineScope, Unit> 获取数据，拿到结果并处理
     * @param handleLoading Boolean 是否处理loading框 默认处理
     * @param loadingMsg String?  loading框的显示文字 不传也没事，app有默认文字
     * @return Job
     */
    fun launch(
        workBlock: suspend CoroutineScope.() -> Unit,
        handleLoading: Boolean = true,
        loadingMsg: String? = null
    ): Job {
        if (handleLoading) {
            liveData_event.value = BaseVMEvent(EventType.SHOW_LOADING, loadingMsg)
        }
        var job = viewModelScope.launch {
            workBlock()
            if (handleLoading) {
                liveData_event.value = BaseVMEvent(EventType.HIDE_LOADING)
            }
        }
        return job
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