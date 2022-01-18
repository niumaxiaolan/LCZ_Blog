package com.lcz.lcz_blog.module.blog.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.dele.kuaiqicha.base.store.AppManager
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.base.BaseIntentBean
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.databinding.ActivityAddBlogBinding
import com.lcz.lcz_blog.databinding.ActivityLoginBinding
import com.lcz.lcz_blog.databinding.ActivityRegisterBinding
import com.lcz.lcz_blog.module.blog.viewmodel.AddBlogViewModel
import com.lcz.lcz_blog.module.mian.activity.MainActivity
import com.lcz.lcz_blog.module.user.activity.RegisterActivity
import com.lcz.lcz_blog.module.user.viewmodel.LoginViewModel
import com.lcz.lcz_blog.store.UserManager
import com.lcz.lcz_blog.util.log.LogUtil
import com.liuchuanzheng.baselib.util.lcz.toast
import com.liuchuanzheng.lcz_wanandroid.base.BaseActivity
import com.liuchuanzheng.lcz_wanandroid.base.Constant

class AddBlogActivity : BaseVMActivity<AddBlogViewModel>() {
    val mViewBinding by lazy { ActivityAddBlogBinding.inflate(layoutInflater) }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, AddBlogActivity::class.java)
            context.startActivity(intent)
        }

    }


    override fun setContentView() {
        setContentView(mViewBinding.root)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTitle()

        mViewBinding.tvAdd.setOnClickListener {
            val title = mViewBinding.etTitle.text.toString()
            val content = mViewBinding.etContent.text.toString()
            when {
                title.isEmpty() -> toast("标题不能为空")
                content.isEmpty() -> toast("内容不能为空")
                else -> {
                    net_add(title, content)
                }
            }
        }
    }

    fun net_add(title: String, content: String) {
        mViewModel.add(title, content).observe(this) {
            if (it.isServerResultOK()) {
                finish()
            }
        }
    }

    private fun initTitle() {
        mViewBinding.layoutTitle.ivBack.setOnClickListener {
            finish()
        }
        mViewBinding.layoutTitle.tvTitle.text = "发布博客"
    }
}