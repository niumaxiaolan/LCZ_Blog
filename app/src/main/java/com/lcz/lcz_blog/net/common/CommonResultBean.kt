package com.lcz.lcz_blog.net.common

import com.lcz.lcz_blog.net.base.BaseResultBean


/**
 * @author 刘传政
 * @date 2021/5/25 15:52
 * QQ:1052374416
 * 电话:18501231486
 * 作用:这里时项目具体的网络返回基类。
 * 注意事项:
 */
const val SUCCESS = 200
class CommonResultBean<T> : BaseResultBean {

    var code = SUCCESS
    var msg: String = ""
    var data: T? = null
    override fun getAppCode(): Int {
        return code
    }

    override fun getAppMsg(): String {
        return msg
    }

    override fun isServerResultOK(): Boolean {
        return code == SUCCESS
    }

    override fun toString(): String {
        return "CommonResultBean(code=$code, msg='$msg', data=$data)"
    }


}