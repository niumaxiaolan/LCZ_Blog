package com.lcz.lcz_blog.module.blog.viewmodel

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
import com.liuchuanzheng.lcz_wanandroid.module.home.repository.UserRepository

/**
 * @author 刘传政
 * @date 2021/9/16 0016 14:44
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
class BlogDetailViewModel : BaseViewModel() {
    private val repository by lazy { BlogRepository() }

    fun getDetail(blogId: Int): MutableLiveData<CommonResultBean<BlogPageListResult.Data>> {
        val resultLiveData = MutableLiveData<CommonResultBean<BlogPageListResult.Data>>()
        launch(
            workBlock = {
                var result = repository.getById(blogId)
                handleResult(result,
                    successBlock = {
                        resultLiveData.value = result
                    },
                    failBlock = {
                        toast("${result.msg}")
                    }

                )
                resultLiveData.value = result //如果所有ui操作都再vm中处理完。是可以不传给activity结果的

            }, true, "正在获取详情..."
        )
        return resultLiveData
    }


}