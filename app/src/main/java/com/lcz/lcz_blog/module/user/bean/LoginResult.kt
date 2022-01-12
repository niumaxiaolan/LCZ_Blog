package com.lcz.lcz_blog.module.user.bean

/**
 * <pre>
 * <img width="128" height="110" src="https://tse3-mm.cn.bing.net/th/id/OIP-C.en-w_oH-yn2UsSRfWnOsGAHaGY?w=198&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7" alt="马刺">
 * 作者:     <a href="https://github.com/liuchuanzheng">刘传政</a>
 * 创建时间:  15:08 2022/1/12 0012
 * QQ:      1052374416
 * 电话:     18501231486
 * 描述:
 * </pre>
 */
data class LoginResult(
    var iconUrl: Any = Any(), // null
    var id: Int = 0, // 3
    var password: Any = Any(), // null
    var phone: String = "", // 18501231486
    var registerTime: String = "", // 2022-01-06 14:44:42
    var updateTime: String = "", // 2022-01-06 14:44:42
    var username: String = "" // 用户oduRM
)