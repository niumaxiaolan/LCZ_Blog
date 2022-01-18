package com.lcz.lcz_blog.module.user.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.kingja.loadsir.core.Transport
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.callback.EmptyCallback
import com.lcz.lcz_blog.callback.LoadingCallback
import com.lcz.lcz_blog.databinding.FragmentBlogListBinding
import com.lcz.lcz_blog.databinding.FragmentMyBinding
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.blog.viewmodel.BlogListFragmentViewModel
import com.lcz.lcz_blog.module.mian.activity.MainActivity
import com.lcz.lcz_blog.module.user.viewmodel.MyFragmentViewModel
import com.lcz.lcz_blog.util.CommonLinearItemDecoration
import com.lcz.lcz_blog.util.GlideUtil
import com.lcz.lcz_blog.util.PageUtil
import com.lcz.lcz_blog.util.RefreshUtil
import com.lcz.lcz_blog.util.log.LogUtil
import com.liuchuanzheng.baselib.util.lcz.LCZUtil
import com.liuchuanzheng.baselib.util.lcz.toast
import com.liuchuanzheng.lcz_wanandroid.base.BaseVMFragment
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * <pre>
 * <img width="128" height="110" src="https://tse3-mm.cn.bing.net/th/id/OIP-C.en-w_oH-yn2UsSRfWnOsGAHaGY?w=198&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7" alt="马刺">
 * 作者:     <a href="https://github.com/liuchuanzheng">刘传政</a>
 * 创建时间:  16:23 2022/1/17 0017
 * QQ:      1052374416
 * 电话:     18501231486
 * 描述:
 * </pre>
 */
class MyFragment : BaseVMFragment<MyFragmentViewModel>() {
    lateinit var mViewBinding: FragmentMyBinding
    override fun creatView(inflater: LayoutInflater?, container: ViewGroup?): View {
        mViewBinding = FragmentMyBinding.inflate(inflater!!, container, false)
        return mViewBinding.root
    }

    override fun onLazyLoad() {
        net_getUserInfo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun net_getUserInfo() {
        mViewModel.getUserInfo().observe(this) {
            if (it.isServerResultOK()) {
                mViewBinding.tvUsername.text = it.data?.username
                GlideUtil.loadHead(activity, it.data?.iconUrl, mViewBinding.ivHead)
            }
        }
    }
}