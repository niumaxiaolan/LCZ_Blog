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
                handleResult(result,
                    successBlock = {
                        resultLiveData.value = result //如果所有ui操作都再vm中处理完。是可以不传给activity结果的


                    },
                    failBlock = {
                        toast("${result.msg}")
                    }

                )

            }, false
        )
        return resultLiveData
    }


}