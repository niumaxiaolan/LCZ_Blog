package com.lcz.lcz_blog.module.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lcz.lcz_blog.base.BaseVMEvent
import com.lcz.lcz_blog.base.EventType
import com.lcz.lcz_blog.module.bus.LoginStatusEvent
import com.lcz.lcz_blog.module.user.bean.LoginResult
import com.lcz.lcz_blog.net.common.CommonResultBean
import com.lcz.lcz_blog.store.UserManager
import com.liuchuanzheng.baselib.util.lcz.toast
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
class RegisterViewModel : BaseViewModel() {
    private val userRepository by lazy { UserRepository() }

    //这里基本只做一些和ui相关的基本操作，比如toast，loading等，更具体的ui操作还是要到activity中
    fun register(phone: String, password: String): MutableLiveData<CommonResultBean<*>> {
        val resultLiveData = MutableLiveData<CommonResultBean<*>>()
        launch(
            workBlock = {
                var result = userRepository.register(phone, password)
                handleResult(result,
                    successBlock = {
                        toast("注册成功")
                        resultLiveData.value = result //如果所有ui操作都再vm中处理完。是可以不传给activity结果的

                    },
                    failBlock = {
                        toast("${result.msg}")
                    }

                )

            }, true, "正在注册..."
        )
        return resultLiveData
    }


}