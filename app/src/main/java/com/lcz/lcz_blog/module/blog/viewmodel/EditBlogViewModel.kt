package com.lcz.lcz_blog.module.blog.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.blog.bean.EditBlogRequest
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
class EditBlogViewModel : BaseViewModel() {
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

    fun editBlog(request: EditBlogRequest): MutableLiveData<CommonResultBean<*>> {
        val resultLiveData = MutableLiveData<CommonResultBean<*>>()
        launch(
            workBlock = {
                var result = repository.updateBlog(request)
                handleResult(result,
                    successBlock = {
                        toast("修改成功")

                        UpdateBlogEvent().apply {
                            LiveEventBus.get(UpdateBlogEvent::class.java).post(this)
                        }


                    },
                    failBlock = {
                        toast("${result.msg}")
                    }

                )
                resultLiveData.value = result //如果所有ui操作都再vm中处理完。是可以不传给activity结果的

            }, true, "正在修改..."
        )
        return resultLiveData
    }

    fun deleteBlog(blogId: Int): MutableLiveData<CommonResultBean<*>> {
        val resultLiveData = MutableLiveData<CommonResultBean<*>>()
        launch(
            workBlock = {
                var result = repository.deleteBlog(blogId)
                handleResult(result,
                    successBlock = {
                        toast("删除成功")

                        UpdateBlogEvent().apply {
                            LiveEventBus.get(UpdateBlogEvent::class.java).post(this)
                        }


                    },
                    failBlock = {
                        toast("${result.msg}")
                    }

                )
                resultLiveData.value = result //如果所有ui操作都再vm中处理完。是可以不传给activity结果的

            }, true, "正在删除..."
        )
        return resultLiveData
    }


}