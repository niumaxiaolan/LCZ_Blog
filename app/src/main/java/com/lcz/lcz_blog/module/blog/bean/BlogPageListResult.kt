package com.lcz.lcz_blog.module.blog.bean

/**
 * <pre>
 * <img width="128" height="110" src="https://tse3-mm.cn.bing.net/th/id/OIP-C.en-w_oH-yn2UsSRfWnOsGAHaGY?w=198&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7" alt="马刺">
 * 作者:     <a href="https://github.com/liuchuanzheng">刘传政</a>
 * 创建时间:  17:40 2022/1/17 0017
 * QQ:      1052374416
 * 电话:     18501231486
 * 描述:
 * </pre>
 */
data class BlogPageListResult(
    var currentPage: Int = 0, // 0
    var dataList: List<Data> = listOf(),
    var pageSize: Int = 0, // 0
    var total: Int = 0 // 0
) {
    data class Data(
        var content: String = "", // string
        var createTime: String = "", // 2022-01-17T09:49:19.393Z
        var id: Int = 0, // 0
        var title: String = "", // string
        var updateTime: String = "", // 2022-01-17T09:49:19.393Z
        var user: User = User(),
        var userId: Int = 0 // 0
    ) {
        data class User(
            var iconUrl: String = "", // string
            var id: Int = 0, // 0
            var password: String = "", // string
            var phone: String = "", // string
            var registerTime: String = "", // 2022-01-17T09:49:19.393Z
            var token: String = "", // string
            var updateTime: String = "", // 2022-01-17T09:49:19.393Z
            var username: String = "" // string
        )
    }
}