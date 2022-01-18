package com.lcz.lcz_blog.module.blog.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dele.kuaiqicha.base.store.AppManager
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.kingja.loadsir.core.Transport
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.base.BaseIntentBean
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.callback.EmptyCallback
import com.lcz.lcz_blog.callback.LoadingCallback
import com.lcz.lcz_blog.databinding.ActivityAddBlogBinding
import com.lcz.lcz_blog.databinding.ActivityLoginBinding
import com.lcz.lcz_blog.databinding.ActivityRegisterBinding
import com.lcz.lcz_blog.databinding.ActivitySearchBlogBinding
import com.lcz.lcz_blog.module.blog.bean.BlogPageListResult
import com.lcz.lcz_blog.module.blog.fragment.BlogListFragment
import com.lcz.lcz_blog.module.blog.viewmodel.AddBlogViewModel
import com.lcz.lcz_blog.module.blog.viewmodel.SearchBlogViewModel
import com.lcz.lcz_blog.module.mian.activity.MainActivity
import com.lcz.lcz_blog.module.user.activity.RegisterActivity
import com.lcz.lcz_blog.module.user.viewmodel.LoginViewModel
import com.lcz.lcz_blog.store.UserManager
import com.lcz.lcz_blog.util.*
import com.lcz.lcz_blog.util.log.LogUtil
import com.liuchuanzheng.baselib.util.lcz.LCZUtil
import com.liuchuanzheng.baselib.util.lcz.toast
import com.liuchuanzheng.lcz_wanandroid.base.BaseActivity
import com.liuchuanzheng.lcz_wanandroid.base.Constant
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

class SearchBlogActivity : BaseVMActivity<SearchBlogViewModel>() {
    val mViewBinding by lazy { ActivitySearchBlogBinding.inflate(layoutInflater) }
    val adapter: MyAdapter by lazy { MyAdapter(null) }
    lateinit var loadService: LoadService<Any>

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, SearchBlogActivity::class.java)
            context.startActivity(intent)
        }

        /**
         * view是需要共享效果的view
         * 否则不会报错,但没有动画效果
         */
        fun startActivityForTransition(activity: Activity, view: View) {
            //共享元素跳转
            val i = Intent(activity, SearchBlogActivity::class.java)
            val pair1 = androidx.core.util.Pair<View, String>(view, "SHARE_VIEW_1")
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1)
            // ActivityCompat是android支持库中用来适应不同android版本的
            ActivityCompat.startActivity(activity, i, optionsCompat.toBundle())
        }
    }


    override fun setContentView() {
        setContentView(mViewBinding.root)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding.ivBack.setOnClickListener {
            finishAfterTransition();
        }
        mViewBinding.etSearch.setFilters(arrayOf<InputFilter>(EmojiFilter()))
        mViewBinding.etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //软盘搜索键
                net_search(true)
                KeyboardUtils.hideSoftInput(activity)
                return@OnEditorActionListener true
            }
            false
        })
        mViewBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                net_search(true)
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().length == 0) {
                    mViewBinding.ivDelete.setVisibility(View.GONE)
                } else {
                    mViewBinding.ivDelete.setVisibility(View.VISIBLE)
                }
            }
        })
        mViewBinding.ivDelete.setOnClickListener {
            mViewBinding.etSearch.setText("")
        }
        loadService = LoadSir.getDefault().register(
            mViewBinding.smartRefreshLayout,
            Callback.OnReloadListener {
                loadService.showCallback(LoadingCallback::class.java)
                net_search(true)
            })
        loadService.showCallback(LoadingCallback::class.java)
        mViewBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        mViewBinding.recyclerView.addItemDecoration(
            CommonLinearItemDecoration(dividerColor = context.getColor(R.color.common_theme), dividerHeight = LCZUtil.dp2px(0.5f))
        )
        mViewBinding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            BlogDetailActivity.startActivity(activity, intentBean = BlogDetailActivity.Companion.IntentBean(this.adapter.getItem(position).id))
        }
        mViewBinding.smartRefreshLayout.setEnableLoadMore(true)
        mViewBinding.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                net_search(true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                net_search(false)
            }
        })
        showKeyboard(activity, mViewBinding.etSearch)
        net_search(true)
    }

    override fun observeViewModel() {
        super.observeViewModel()
        mViewModel.liveData_complete.observe(this) {
            mViewBinding.smartRefreshLayout.finishRefresh()
            mViewBinding.smartRefreshLayout.finishLoadMore()
        }
    }

    fun net_search(isRefresh: Boolean) {
        if (isRefresh) {
            mViewBinding.smartRefreshLayout.setNoMoreData(false)
        }
        mViewModel.searchBlog(
            PageUtil.getNextServerPageBean(isRefresh, adapter.data.size),
            mViewBinding.etSearch.text.toString().trim()
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
            val mHightLightText = "${mViewBinding.etSearch.text.toString().trim()}"
            val spannableStringBuilder =
                LCZUtil.stringToHighLight(item.title, mHightLightText, false, "#ed2b2f")
            holder.setText(R.id.tv_title, spannableStringBuilder)
            holder.setText(R.id.tv_content, item.content)
            holder.setText(R.id.tv_username, item.user.username)
            holder.setText(R.id.tv_date, item.createTime)
            GlideUtil.loadHead(context, item.user.iconUrl, holder.getView(R.id.iv_icon))
        }
    }
}