package com.lcz.lcz_blog.module.blog.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lcz.lcz_blog.module.blog.bean.AddBlogRequest
import com.lcz.lcz_blog.module.blog.repository.BlogRepository
import com.lcz.lcz_blog.module.bus.UpdateBlogEvent
import com.lcz.lcz_blog.net.common.CommonResultBean
import com.liuchuanzheng.baselib.util.lcz.toast
import com.liuchuanzheng.lcz_wanandroid.base.BaseViewModel

/**
 * @author 刘传政
 * @date 2021/9/16 0016 14:44
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
class AddBlogViewModel : BaseViewModel() {
    private val repository by lazy { BlogRepository() }

    fun add(title: String, content: String): MutableLiveData<CommonResultBean<Int>> {
        var addBlogRequest = AddBlogRequest(content,title)
        val resultLiveData = MutableLiveData<CommonResultBean<Int>>()
        launch(
            workBlock = {
                var result = repository.add(addBlogRequest)
                handleResult(result,
                    successBlock = {
                        toast("发布成功")

                        UpdateBlogEvent().apply {
                            LiveEventBus.get(UpdateBlogEvent::class.java).post(this)
                        }


                    },
                    failBlock = {
                        toast("${result.msg}")
                    }

                )
                resultLiveData.value = result //如果所有ui操作都再vm中处理完。是可以不传给activity结果的

            }, true, "正在发布..."
        )
        return resultLiveData
    }


}