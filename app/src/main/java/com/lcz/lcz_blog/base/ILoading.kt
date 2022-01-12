package com.liuchuanzheng.lcz_wanandroid.base


/**
 * @author 刘传政
 * @date 2021/9/17 0017 11:43
 * QQ:1052374416
 * 电话:18501231486
 * 作用:加载对话框的方法定义
 * 注意事项:
 */
interface ILoading {
    fun showLoading(msg: String = "")

    fun hideLoading()
}