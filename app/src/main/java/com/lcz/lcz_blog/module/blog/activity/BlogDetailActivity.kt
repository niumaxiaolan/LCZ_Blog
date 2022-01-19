package com.lcz.lcz_blog.module.blog.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.dele.kuaiqicha.base.store.AppManager
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.base.BaseIntentBean
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.databinding.ActivityAddBlogBinding
import com.lcz.lcz_blog.databinding.ActivityBlogDetailBinding
import com.lcz.lcz_blog.databinding.ActivityLoginBinding
import com.lcz.lcz_blog.databinding.ActivityRegisterBinding
import com.lcz.lcz_blog.module.blog.viewmodel.AddBlogViewModel
import com.lcz.lcz_blog.module.blog.viewmodel.BlogDetailViewModel
import com.lcz.lcz_blog.module.mian.activity.MainActivity
import com.lcz.lcz_blog.module.user.activity.LoginActivity
import com.lcz.lcz_blog.module.user.activity.RegisterActivity
import com.lcz.lcz_blog.module.user.viewmodel.LoginViewModel
import com.lcz.lcz_blog.store.UserManager
import com.lcz.lcz_blog.util.GlideUtil
import com.lcz.lcz_blog.util.log.LogUtil
import com.liuchuanzheng.baselib.util.lcz.toast
import com.liuchuanzheng.lcz_wanandroid.base.BaseActivity
import com.liuchuanzheng.lcz_wanandroid.base.Constant

class BlogDetailActivity : BaseVMActivity<BlogDetailViewModel>() {
    val mViewBinding by lazy { ActivityBlogDetailBinding.inflate(layoutInflater) }
    var intentBean: IntentBean = IntentBean(0)

    companion object {
        fun startActivity(context: Context, intentBean: IntentBean) {
            val intent = Intent(context, BlogDetailActivity::class.java)
            intent.putExtra(Constant.IntentKey.IntentBean, intentBean)
            context.startActivity(intent)
        }

        /**
         * view是需要共享效果的view
         * 否则不会报错,但没有动画效果
         */
        fun startActivityForTransition(activity: Activity, intentBean: IntentBean, view_title: View, view_user: View, view_content: View) {
            //共享元素跳转
            val i = Intent(activity, BlogDetailActivity::class.java)
            i.putExtra(Constant.IntentKey.IntentBean, intentBean)
            val pair1 = androidx.core.util.Pair<View, String>(view_title, "SHARE_VIEW_1")
            val pair2 = androidx.core.util.Pair<View, String>(view_user, "SHARE_VIEW_2")
            val pair3 = androidx.core.util.Pair<View, String>(view_content, "SHARE_VIEW_3")
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1, pair2, pair3)
            // ActivityCompat是android支持库中用来适应不同android版本的
            ActivityCompat.startActivity(activity, i, optionsCompat.toBundle())
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
        mViewBinding.layoutTitle.ivBack.setOnClickListener {
            finish()
        }
        net_getDetail()
    }

    fun net_getDetail() {
        mViewModel.getDetail(intentBean.blogId).observe(this) {
            if (it.isServerResultOK()) {
                it.data?.let {
                    mViewBinding.layoutTitle.tvTitle.text = it.title
                    GlideUtil.loadHead(activity, it.user.iconUrl, mViewBinding.ivIcon)
                    mViewBinding.tvUsername.text = it.user.username
                    mViewBinding.tvDate.text = it.createTime
                    mViewBinding.tvContent.text = it.content

                }

            }
        }
    }

}