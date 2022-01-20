package com.lcz.lcz_blog.module.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lcz.lcz_blog.module.blog.bean.AddBlogRequest
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.bus.LoginStatusEvent
import com.lcz.lcz_blog.module.user.bean.LoginResult
import com.lcz.lcz_blog.net.common.CommonResultBean
import com.lcz.lcz_blog.store.UserManager
import com.liuchuanzheng.baselib.util.lcz.toast
import com.liuchuanzheng.lcz_wanandroid.base.BaseViewModel
import com.lcz.lcz_blog.module.blog.repository.BlogRepository
import com.lcz.lcz_blog.module.bus.UpdateBlogEvent
import com.lcz.lcz_blog.util.PageBean
import com.liuchuanzheng.lcz_wanandroid.module.home.repository.UserRepository

/**
 * @author 刘传政
 * @date 2021/9/16 0016 14:44
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
class ChangeIconViewModel : BaseViewModel() {
    private val repository by lazy { UserRepository() }
    fun changeIcon(iconUrl: String): MutableLiveData<CommonResultBean<*>> {
        val resultLiveData = MutableLiveData<CommonResultBean<*>>()
        launch(
            workBlock = {
                var result = repository.changeIcon(iconUrl)
                resultLiveData.value = result //因为列表的ui操作太复杂，所以这里不处理了，返给view层

            }, true,"正在修改..."
        )
        return resultLiveData
    }


}