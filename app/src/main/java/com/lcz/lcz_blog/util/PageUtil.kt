package com.lcz.lcz_blog.util

/**
 * @author 刘传政
 * @date 2020/6/23 09:30
 * QQ:1052374416
 * 电话:18501231486
 * 作用:默认的页数相关数值的类
 * 注意事项:
 */

const val SERVER_FIRST_PAGE = 1
const val COMMON_PAGE_SIZE = 3

object PageUtil {
    fun getCurrentPage(totalListCount: Int, pageSize: Int = COMMON_PAGE_SIZE): Int {
        var currentPage = 0
        if (totalListCount % pageSize == 0) {
            currentPage = totalListCount / pageSize
        } else {
            currentPage = (totalListCount / pageSize) + 1
        }
        return currentPage
    }

    fun getNextServerPage(isRefresh: Boolean, totalListCount: Int, pageSize: Int = 20): Int {
        if (isRefresh) {
            return SERVER_FIRST_PAGE
        } else {
            var currentPage = getCurrentPage(totalListCount, pageSize)
            return currentPage + 1
        }
    }

    fun isLocalDefaultPage(totalListCount: Int): Boolean {
        return totalListCount == 0
    }

    fun getNextServerPageBean(isRefresh: Boolean, totalListCount: Int, pageSize: Int = COMMON_PAGE_SIZE): PageBean {
        var pageBean = PageBean()
        if (isRefresh) {
            pageBean.pageNo = SERVER_FIRST_PAGE
            pageBean.pageSize = pageSize
        } else {
            var currentPage = getCurrentPage(totalListCount, pageSize)
            pageBean.pageNo = currentPage + 1
            pageBean.pageSize = pageSize
        }
        return pageBean
    }
}

class PageBean {
    var pageNo: Int = SERVER_FIRST_PAGE
    var pageSize: Int = COMMON_PAGE_SIZE
}