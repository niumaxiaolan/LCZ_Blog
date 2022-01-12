package com.lcz.lcz_blog.module.user.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.module.bus.LoginStatusEvent
import com.lcz.lcz_blog.module.user.bean.LoginResult
import com.lcz.lcz_blog.net.common.CommonResultBean
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
class LoginViewModel : BaseViewModel() {
    val liveData_loading = MutableLiveData<Boolean>()
    private val userRepository by lazy { UserRepository() }

    //这里基本只做一些和ui相关的基本操作，比如toast，loading等，更具体的ui操作还是要到activity中
    fun login(phone: String, password: String): MutableLiveData<CommonResultBean<LoginResult>> {
        val resultLiveData = MutableLiveData<CommonResultBean<LoginResult>>()
        liveData_loading.value = true
        launch(
            workBlock = {
                var result = userRepository.login(phone, password)
                handleResult(result,
                    successBlock = {
                        toast("登录成功")
                        resultLiveData.value = result
                        val event = LoginStatusEvent().apply {
                            isLogin = true
                        }
                        LiveEventBus.get(LoginStatusEvent::class.java).post(event)
                    },
                    failBlock = {
                        toast("${result.msg}")
                    }
                )
            }, errorBlock = {
                toast(R.string.net_error)
            }, finillyBlock = {
                liveData_loading.value = false
            }
        )
        return resultLiveData
    }


}