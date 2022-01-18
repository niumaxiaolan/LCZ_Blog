package com.lcz.lcz_blog.module.blog.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.kingja.loadsir.core.Transport
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.callback.EmptyCallback
import com.lcz.lcz_blog.callback.LoadingCallback
import com.lcz.lcz_blog.databinding.FragmentBlogListBinding
import com.lcz.lcz_blog.module.blog.activity.AddBlogActivity
import com.lcz.lcz_blog.module.blog.activity.SearchBlogActivity
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.blog.viewmodel.BlogListFragmentViewModel
import com.lcz.lcz_blog.module.bus.UpdateBlogEvent
import com.lcz.lcz_blog.util.CommonLinearItemDecoration
import com.lcz.lcz_blog.util.PageUtil
import com.lcz.lcz_blog.util.RefreshUtil
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
class BlogListFragment : BaseVMFragment<BlogListFragmentViewModel>() {
    lateinit var mViewBinding: FragmentBlogListBinding
    val adapter: MyAdapter by lazy { MyAdapter(null) }
    lateinit var loadService: LoadService<Any>
    override fun creatView(inflater: LayoutInflater?, container: ViewGroup?): View {
        mViewBinding = FragmentBlogListBinding.inflate(inflater!!, container, false)
        return mViewBinding.root
    }

    override fun onLazyLoad() {
        getPageList(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadService = LoadSir.getDefault().register(
            mViewBinding.smartRefreshLayout,
            Callback.OnReloadListener {
                loadService.showCallback(LoadingCallback::class.java)
                getPageList(true)
            })
        loadService.showCallback(LoadingCallback::class.java)
        mViewBinding.recyclerView.setLayoutManager(LinearLayoutManager(requireActivity()))
        mViewBinding.recyclerView.addItemDecoration(
            CommonLinearItemDecoration(dividerColor = Color.parseColor("#EEEEEE"), dividerHeight = LCZUtil.dp2px(1f))
        )
        mViewBinding.recyclerView.adapter = adapter
        mViewBinding.smartRefreshLayout.setEnableLoadMore(true)
        mViewBinding.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                getPageList(true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getPageList(false)
            }

        })
        mViewBinding.ivAdd.setOnClickListener {
            AddBlogActivity.startActivity(requireContext())
        }
        mViewBinding.llSearch.setOnClickListener {
            SearchBlogActivity.startActivityForTransition(requireActivity(), mViewBinding.llSearch)
        }
        initBus()
    }

    private fun initBus() {
        //监听事件总线的消息
        LiveEventBus
            .get(UpdateBlogEvent::class.java)
            .observe(this, Observer {
                getPageList(true)
            })
    }

    fun getPageList(isRefresh: Boolean) {
        mViewModel.getPageList(PageUtil.getNextServerPageBean(isRefresh, adapter.data.size))
            .observe(viewLifecycleOwner) {
                RefreshUtil.changeRefreshViewStatus(
                    mViewBinding.smartRefreshLayout,
                    it?.data?.dataList?.size ?: 0,
                )
                if (it.isServerResultOK()) {
                    if (isRefresh) {
                        adapter.setNewInstance(it.data?.dataList?.toMutableList())
                    } else {
                        adapter.addData(it.data?.dataList!!.toMutableList())
                    }
                } else {
                    toast(it.msg)
                }

                RefreshUtil.changeLoadServiceStatus(it.code, loadService, adapter.data.size)
            }
        mViewModel.liveData_complete.observe(viewLifecycleOwner) {
            mViewBinding.smartRefreshLayout.finishRefresh()
            mViewBinding.smartRefreshLayout.finishLoadMore()
        }
    }

    class MyAdapter(
        data: MutableList<BlogPageListResult.Data>?
    ) :
        BaseQuickAdapter<BlogPageListResult.Data, BaseViewHolder>(
            R.layout.rv_item_blog,
            data
        ) {
        override fun convert(holder: BaseViewHolder, item: BlogPageListResult.Data) {
            holder.setText(R.id.tv_title, item.title)
            holder.setText(R.id.tv_content, item.content)
            holder.setText(R.id.tv_username, item.user.username)
            holder.setText(R.id.tv_date, item.createTime)
        }
    }
}