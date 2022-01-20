package com.lcz.lcz_blog.module.blog.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lcz.lcz_blog.base.BaseIntentBean
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.databinding.ActivityEditBlogBinding
import com.lcz.lcz_blog.module.blog.bean.EditBlogRequest
import com.lcz.lcz_blog.module.blog.viewmodel.EditBlogViewModel
import com.liuchuanzheng.baselib.util.lcz.toast
import com.liuchuanzheng.lcz_wanandroid.base.Constant

class EditBlogActivity : BaseVMActivity<EditBlogViewModel>() {
    val mViewBinding by lazy { ActivityEditBlogBinding.inflate(layoutInflater) }
    var intentBean: IntentBean = IntentBean(0)

    companion object {
        fun startActivity(context: Context, intentBean: IntentBean) {
            val intent = Intent(context, EditBlogActivity::class.java)
            intent.putExtra(Constant.IntentKey.IntentBean, intentBean)
            context.startActivity(intent)
        }

        data class IntentBean(var blogId: Int) : BaseIntentBean()
    }


    override fun setContentView() {
        setContentView(mViewBinding.root)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseIntent<IntentBean>()?.apply {
            intentBean = this
        }
        initTitle()
        mViewBinding.tvEdit.setOnClickListener {
            val title = mViewBinding.etTitle.text.toString()
            val content = mViewBinding.etContent.text.toString()
            when {
                title.isEmpty() -> toast("标题不能为空")
                content.isEmpty() -> toast("内容不能为空")
                else -> {
                    net_editBlog(title, content)
                }
            }
        }
        mViewBinding.tvDelete.setOnClickListener {
            net_deleteBlog()
        }
        net_getDetail()
    }

    fun net_getDetail() {
        mViewModel.getDetail(intentBean.blogId).observe(this) {
            if (it.isServerResultOK()) {
                it.data?.let {
                    mViewBinding.etTitle.setText(it.title)
                    mViewBinding.etContent.setText(it.content)
                }

            }
        }
    }

    fun net_editBlog(title: String, content: String) {
        var request = EditBlogRequest()
        request.title = title
        request.content = content
        request.id = intentBean.blogId
        mViewModel.editBlog(request).observe(this) {
            if (it.isServerResultOK()) {
                finish()
            }
        }
    }

    fun net_deleteBlog() {
        mViewModel.deleteBlog(intentBean.blogId).observe(this) {
            if (it.isServerResultOK()) {
                finish()
            }
        }
    }

    private fun initTitle() {
        mViewBinding.layoutTitle.ivBack.setOnClickListener {
            finishAfterTransition()
        }
        mViewBinding.layoutTitle.tvTitle.text = "发布博客"
    }
}