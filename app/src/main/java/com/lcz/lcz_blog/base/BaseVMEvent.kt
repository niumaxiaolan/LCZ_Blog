package com.lcz.lcz_blog.base

/**
 * <pre>
 * <img width="128" height="110" src="https://tse3-mm.cn.bing.net/th/id/OIP-C.en-w_oH-yn2UsSRfWnOsGAHaGY?w=198&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7" alt="马刺">
 * 作者:     <a href="https://github.com/liuchuanzheng">刘传政</a>
 * 创建时间:  16:07 2022/1/12 0012
 * QQ:      1052374416
 * 电话:     18501231486
 * 描述:
 * </pre>
 */
enum class EventType {
    SHOW_LOADING,
    HIDE_LOADING,
}

data class BaseVMEvent(var eventType: EventType, var data: Any? = null)