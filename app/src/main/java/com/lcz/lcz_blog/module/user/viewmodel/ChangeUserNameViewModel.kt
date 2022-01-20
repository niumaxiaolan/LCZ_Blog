package com.lcz.lcz_blog.module.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.lcz.lcz_blog.net.common.CommonResultBean
import com.liuchuanzheng.lcz_wanandroid.base.BaseViewModel
import com.liuchuanzheng.lcz_wanandroid.module.home.repository.UserRepository

/**
 * @author 刘传政
 * @date 2021/9/16 0016 14:44
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
class ChangeUserNameViewModel : BaseViewModel() {
    private val repository by lazy { UserRepository() }
    fun changeUsername(username: String): MutableLiveData<CommonResultBean<*>> {
        val resultLiveData = MutableLiveData<CommonResultBean<*>>()
        launch(
            workBlock = {
                var result = repository.changeUsername(username)
                resultLiveData.value = result //因为列表的ui操作太复杂，所以这里不处理了，返给view层

            }, true, "正在修改..."
        )
        return resultLiveData
    }


}