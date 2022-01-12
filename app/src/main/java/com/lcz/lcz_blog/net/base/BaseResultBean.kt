package com.lcz.lcz_blog.net.base

/**
 * @author 刘传政
 * @date 2019-06-03 13:42
 * QQ:1052374416
 * telephone:18501231486
 * 作用:这里只定义需要的必要信息.其他自定义json都由app自己实现
 * 注意事项:
 */
interface BaseResultBean {
    fun getAppCode(): Int
    fun getAppMsg(): String

    /**
     * 判断结果是否正确
     * 因为后台人员有各种告知正确的方法,不一定是通过appcode
     */
    fun isServerResultOK(): Boolean
}