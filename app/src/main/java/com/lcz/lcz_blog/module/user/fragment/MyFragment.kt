package com.lcz.lcz_blog.module.user.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lcz.lcz_blog.databinding.FragmentMyBinding
import com.lcz.lcz_blog.module.bus.UpdataUserInfoEvent
import com.lcz.lcz_blog.module.user.activity.*
import com.lcz.lcz_blog.module.user.viewmodel.MyFragmentViewModel
import com.lcz.lcz_blog.store.UserManager
import com.lcz.lcz_blog.util.GlideUtil
import com.liuchuanzheng.baselib.util.lcz.toast
import com.liuchuanzheng.lcz_wanandroid.base.BaseVMFragment

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
        mViewBinding.ivHead.setOnClickListener {
            ChangeIconActivity.startActivityForTransition(requireActivity(), mViewBinding.ivHead)
        }
        mViewBinding.tvUsername.setOnClickListener {
            ChangeUserNameActivity.startActivity(requireActivity())
        }
        mViewBinding.tvLogout.setOnClickListener {
            toast("已退出账号")
            UserManager.cleanUserInfo()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        mViewBinding.mivCollect.setOnClickListener {
            MyCollectBlogsActivity.startActivity(requireContext())
        }
        mViewBinding.mivMyAdded.setOnClickListener {
            MyAddedBlogsActivity.startActivity(requireContext())
        }
        initBus()
    }

    fun net_getUserInfo() {
        mViewModel.getUserInfo().observe(viewLifecycleOwner) {
            if (it.isServerResultOK()) {
                mViewBinding.tvUsername.text = it.data?.username
                GlideUtil.loadHead(activity, it.data?.iconUrl, mViewBinding.ivHead)
            }
        }
    }
    private fun initBus() {
        //监听事件总线的消息
        LiveEventBus
            .get(UpdataUserInfoEvent::class.java)
            .observe(this, Observer {
                net_getUserInfo()
            })
    }
}