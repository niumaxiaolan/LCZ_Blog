package com.lcz.lcz_blog.module.blog.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.databinding.FragmentBlogListBinding
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.blog.viewmodel.BlogListFragmentViewModel
import com.lcz.lcz_blog.util.PageUtil
import com.lcz.lcz_blog.util.log.LogUtil
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
    override fun creatView(inflater: LayoutInflater?, container: ViewGroup?): View {
        mViewBinding = FragmentBlogListBinding.inflate(inflater!!, container, false)
        return mViewBinding.root
    }

    override fun onLazyLoad() {
        getPageList(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewBinding.recyclerView.setLayoutManager(LinearLayoutManager(requireActivity()))
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
    }

    fun getPageList(isRefresh: Boolean) {
        mViewModel.getPageList(PageUtil.getNextServerPageBean(isRefresh, adapter.data.size,2)).observe(this) {

            if (isRefresh) {
                adapter.setNewInstance(it.data?.dataList?.toMutableList())
            }else{
                adapter.addData(it.data?.dataList!!.toMutableList())
            }

        }
        mViewModel.liveData_complete.observe(this){
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
        }
    }
}