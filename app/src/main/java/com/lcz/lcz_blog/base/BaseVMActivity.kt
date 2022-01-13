package com.lcz.lcz_blog.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.util.GenericSuperclassUtil
import com.liuchuanzheng.lcz_wanandroid.base.BaseActivity
import com.liuchuanzheng.lcz_wanandroid.base.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * @author 刘传政
 * @date 2021/9/14 0014 16:20
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {
    val mViewModel: VM by lazy {
        //获取类的第一个泛型Class
        val classVM = GenericSuperclassUtil.getActualTypeArgument(this.javaClass, 0) as Class<VM>
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(classVM)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }


    /**
     * 进行观察订阅
     * 主要是观察一些通用的数据，比如loading
     * 至于每个接口的结果，viewmodel都是每次创建一个livedata,所以用的时候再观察就行
     */
    fun observeViewModel() {
        mViewModel.liveData_event.observe(this) {
            when (it.eventType) {
                EventType.SHOW_LOADING -> {
                    if (it.data == null) {
                        showLoading(it.data as String)
                    } else {
                        showLoading()
                    }

                }
                EventType.HIDE_LOADING -> {
                    hideLoading()
                }
            }
        }
    }

}