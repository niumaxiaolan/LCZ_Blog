package com.lcz.lcz_blog.module.user.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.callback.LoadingCallback
import com.lcz.lcz_blog.databinding.ActivityMyAddedBlogsBinding
import com.lcz.lcz_blog.module.blog.activity.BlogDetailActivity
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.bus.UpdateBlogEvent
import com.lcz.lcz_blog.module.user.viewmodel.MyAddedBlogsViewModel
import com.lcz.lcz_blog.util.CommonLinearItemDecoration
import com.lcz.lcz_blog.util.GlideUtil
import com.lcz.lcz_blog.util.PageUtil
import com.lcz.lcz_blog.util.RefreshUtil
import com.liuchuanzheng.baselib.util.lcz.LCZUtil
import com.liuchuanzheng.baselib.util.lcz.toast
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 * @author: 刘传政
 * @date: 2022/1/20 0020 16:28
 * QQ:1052374416
 * 作用:我的发布
 * 注意事项:
 */
class MyAddedBlogsActivity : BaseVMActivity<MyAddedBlogsViewModel>() {
    val mViewBinding by lazy { ActivityMyAddedBlogsBinding.inflate(layoutInflater) }
    val adapter: MyAdapter by lazy { MyAdapter(null) }
    lateinit var loadService: LoadService<Any>

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, MyAddedBlogsActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun setContentView() {
        setContentView(mViewBinding.root)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTitle()
        loadService = LoadSir.getDefault().register(
            mViewBinding.smartRefreshLayout,
            Callback.OnReloadListener {
                loadService.showCallback(LoadingCallback::class.java)
                net_query_my_added_blogs(true)
            })
        loadService.showCallback(LoadingCallback::class.java)
        mViewBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        mViewBinding.recyclerView.addItemDecoration(
            CommonLinearItemDecoration(
                dividerColor = context.getColor(R.color.common_theme),
                dividerHeight = LCZUtil.dp2px(0.5f)
            )
        )
        mViewBinding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            BlogDetailActivity.startActivity(
                activity,
                intentBean = BlogDetailActivity.Companion.IntentBean(this.adapter.getItem(position).id)
            )
        }
        //这里就不写收藏，取消收藏的逻辑了。麻烦。首页写过了
        mViewBinding.smartRefreshLayout.setEnableLoadMore(true)
        mViewBinding.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                net_query_my_added_blogs(true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                net_query_my_added_blogs(false)
            }
        })
        initBus()
        net_query_my_added_blogs(true)
    }

    private fun initTitle() {
        mViewBinding.layoutTitle.ivBack.setOnClickListener {
            finish()
        }
        mViewBinding.layoutTitle.tvTitle.text = "我的发布"
    }

    private fun initBus() {
        //监听事件总线的消息
        LiveEventBus
            .get(UpdateBlogEvent::class.java)
            .observe(this, Observer {
                net_query_my_added_blogs(true)
            })
    }

    override fun observeViewModel() {
        super.observeViewModel()
        mViewModel.liveData_complete.observe(this) {
            mViewBinding.smartRefreshLayout.finishRefresh()
            mViewBinding.smartRefreshLayout.finishLoadMore()
        }
    }

    fun net_query_my_added_blogs(isRefresh: Boolean) {
        if (isRefresh) {
            mViewBinding.smartRefreshLayout.setNoMoreData(false)
        }
        mViewModel.query_my_added_blogs(
            PageUtil.getNextServerPageBean(isRefresh, adapter.data.size)
        )
            .observe(this) {
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

    }

    inner class MyAdapter(
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
            GlideUtil.loadHead(context, item.user.iconUrl, holder.getView(R.id.iv_icon))
            holder.setText(R.id.tv_collect_count, item.collectCount.toString())
            var iv_collect = holder.getView<ImageView>(R.id.iv_collect)
            if (item.isMyCollected == 0) {
                iv_collect.setImageResource(R.drawable.ic_uncollect)
            } else {
                iv_collect.setImageResource(R.drawable.ic_collected)
            }
        }
    }
}