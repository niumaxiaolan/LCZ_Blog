package com.lcz.lcz_blog.module.user.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lcz.lcz_blog.base.BaseIntentBean
import com.lcz.lcz_blog.databinding.ActivityLoginBinding
import com.liuchuanzheng.lcz_wanandroid.base.BaseActivity
import com.liuchuanzheng.lcz_wanandroid.base.Constant

class RegisterActivity : BaseActivity() {
    lateinit var mViewBinding: ActivityLoginBinding
    var intentBean: IntentBean = IntentBean()

    companion object {
        fun startActivity(context: Context, intentBean: IntentBean?) {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.putExtra(Constant.IntentKey.IntentBean, intentBean)
            context.startActivity(intent)
        }

        data class IntentBean(var msg: String = "") : BaseIntentBean()
    }

    override fun setContentView() {
        mViewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseIntent<IntentBean>()?.apply {
            intentBean = this
        }
    }
}