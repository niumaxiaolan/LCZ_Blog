package com.liuchuanzheng.lcz_wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lcz.lcz_blog.util.dialog.DialogUtil


abstract class BaseFragment : Fragment(), ILoading {
    private lateinit var loadingDialog: DialogUtil

    //Fragment是否已经执行过懒加载
    private var isLazyLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return creatView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = DialogUtil(activity)
    }

    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!isLazyLoaded) {
            onLazyLoad()
            isLazyLoaded = true
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    /**
     * 因为系统自带的设置view方法有很多种,id,view都可以.所以这里不像
     * 大家都用id的封装方式,那是限制了自己
     * @param inflater
     * @param container
     */
    //inflater.inflate(R.layout.fragment_accepted, container, false);
    protected abstract fun creatView(inflater: LayoutInflater?, container: ViewGroup?): View

    /**
     * 当页面可见的时候，才加载当前页面。 没有打开的页面，就不会预加载。
     *
     *
     * 说白了，懒加载就是可见的时候才去请求数据。
     * 只回调一次
     */
    protected abstract fun onLazyLoad()
    override fun showLoading(msg: String) {
        loadingDialog.showLoading(msg)
    }

    override fun hideLoading() {
        loadingDialog.dismissLoading()
    }
}