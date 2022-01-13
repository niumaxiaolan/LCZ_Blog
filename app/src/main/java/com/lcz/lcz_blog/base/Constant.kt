package com.liuchuanzheng.lcz_wanandroid.base

/**
 * @author 刘传政
 * @date 2021/5/18 17:22
 * QQ:1052374416
 * 电话:18501231486
 * 作用:所有的常量类。这里每个常量不用大写表示，因为个人不喜欢，看大写字母一眼看不明白
 * 注意事项:采用kotlin object方式是因为这样的话既能引用,也能写逻辑代码.
 */
object Constant {
    object Url {
        /**
         * 这个url是gradle中配置的。可以通过指定打包方式选择不同的值
         * 网络请求的baseUrl
         */
        var request_base_url: String = "http://192.168.1.127:10001" //本地服务器
//        var request_base_url: String = "http://101.37.76.6:10001" //阿里云服务器
    }
    /**
     * intent传递字段
     */
    object IntentKey {
        /**
         * 默认activity传递对象时的key
         */
        const val IntentBean = "IntentBean"

        /**
         * 默认activity返回对象时的key
         */
        const val response_IntentBean = "response_IntentBean"
    }
    /**
     * Tag
     */
    object Tag {
        /**
         * 打印log的tag
         */
        const val tag = "刘博客"
    }
    /**
     * sp相关的常量
     */
    object SP {
        object UserInfo{
            /**
             * sp名字
             * 用户信息相关
             */
            const val spName = "userInfo"
            object Key{
                /**
                 * 数据的key
                 */
                const val data = "data"
            }
        }
        object AppStatus{
            /**
             * sp名字
             * 用户信息相关
             */
            const val spName = "AppStatus"
            object Key{
                /**
                 * 数据的key
                 */
                const val splash = "splash"
            }
        }

    }

}