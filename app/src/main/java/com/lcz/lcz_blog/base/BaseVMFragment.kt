package com.liuchuanzheng.lcz_wanandroid.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.lcz.lcz_blog.util.GenericSuperclassUtil

/**
 * @author 刘传政
 * @date 2021/9/14 0014 18:14
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {
    val mViewModel: VM by lazy {
        //获取类的第一个泛型Class
        val classVM = GenericSuperclassUtil.getActualTypeArgument(this.javaClass, 0) as Class<VM>
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(classVM)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    /**
     * 进行观察订阅
     */
    abstract fun observeViewModel()

}