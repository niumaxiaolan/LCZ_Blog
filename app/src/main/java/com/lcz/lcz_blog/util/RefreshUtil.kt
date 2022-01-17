package com.lcz.lcz_blog.util

import com.kingja.loadsir.core.LoadService
import com.lcz.lcz_blog.callback.EmptyCallback
import com.lcz.lcz_blog.callback.ErrorCallback
import com.lcz.lcz_blog.net.common.CODE_FAIL
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * @author 刘传政
 * @date 2020/9/9 14:50
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
object RefreshUtil {
    fun changeRefreshViewStatus(
        smartRefreshLayout: SmartRefreshLayout,
        size: Int,//本次请求的实际条数
    ) {
        try {
            smartRefreshLayout.finishRefresh()
            smartRefreshLayout.finishLoadMore()
            if (size < COMMON_PAGE_SIZE) {
                //全部加载完成<已经没有数据>
                smartRefreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                //加载完成<还有数据>
                smartRefreshLayout.setNoMoreData(false)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 刷新状态布局
     */
    fun changeLoadServiceStatus(
        code: Int,
        loadService: LoadService<Any>,
        currentListSize: Int
    ) {
        if (code == CODE_FAIL) {
            //网络错误时.空数据显示网络错误图
            if (PageUtil.isLocalDefaultPage(currentListSize)) {
                loadService.showCallback(ErrorCallback::class.java)
            } else {
                loadService.showSuccess()
            }
        } else {
            //其他情况,空就显示空,有就显示有
            if (PageUtil.isLocalDefaultPage(currentListSize)) {
                loadService.showCallback(EmptyCallback::class.java)
            } else {
                loadService.showSuccess()
            }
        }
    }
}