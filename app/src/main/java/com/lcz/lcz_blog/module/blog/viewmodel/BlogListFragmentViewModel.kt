package com.lcz.lcz_blog.module.blog.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.blog.repository.BlogRepository
import com.lcz.lcz_blog.net.common.CommonResultBean
import com.lcz.lcz_blog.util.PageBean
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
class BlogListFragmentViewModel : BaseViewModel() {
    private val repository by lazy { BlogRepository() }
    val liveData_complete = MutableLiveData<Any>()

    fun getPageList(page: PageBean): MutableLiveData<CommonResultBean<BlogPageListResult>> {
        val resultLiveData = MutableLiveData<CommonResultBean<BlogPageListResult>>()
        launch(
            workBlock = {
                var result = repository.getPageList(page)
                liveData_complete.value = true
                resultLiveData.value = result //因为列表的ui操作太复杂，所以这里不处理了，返给view层

            }, false
        )
        return resultLiveData
    }

    fun collect(blogId: Int, type: Int): MutableLiveData<CommonResultBean<*>> {
        val resultLiveData = MutableLiveData<CommonResultBean<*>>()
        launch(
            workBlock = {
                var result = repository.collect(blogId, type)
                resultLiveData.value = result //因为列表的ui操作太复杂，所以这里不处理了，返给view层

            }, false
        )
        return resultLiveData
    }


}