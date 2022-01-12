package com.lcz.lcz_blog.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lcz.lcz_blog.util.GenericSuperclassUtil
import com.liuchuanzheng.lcz_wanandroid.base.BaseActivity
import java.lang.reflect.ParameterizedType

/**
 * @author 刘传政
 * @date 2021/9/14 0014 16:20
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
abstract class BaseVMActivity<VM : ViewModel> : BaseActivity() {
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
     */
    abstract fun observeViewModel()

}