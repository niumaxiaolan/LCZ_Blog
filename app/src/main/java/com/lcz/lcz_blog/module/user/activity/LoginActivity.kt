package com.lcz.lcz_blog.module.user.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lcz.lcz_blog.base.BaseIntentBean
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.base.Constant
import com.lcz.lcz_blog.databinding.ActivityLoginBinding
import com.lcz.lcz_blog.module.mian.activity.MainActivity
import com.lcz.lcz_blog.module.user.viewmodel.LoginViewModel
import com.lcz.lcz_blog.util.StringUtils
import com.lcz.lcz_blog.util.log.LogUtil
import com.liuchuanzheng.baselib.util.lcz.toast

class LoginActivity : BaseVMActivity<LoginViewModel>() {
    val mViewBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    var intentBean: IntentBean = IntentBean()

    companion object {
        fun startActivity(context: Context, intentBean: IntentBean) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra(Constant.IntentKey.IntentBean, intentBean)
            context.startActivity(intent)
        }

        data class IntentBean(var msg: String = "") : BaseIntentBean()
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
        mViewBinding.ivDeletePhone.setOnClickListener {
            mViewBinding.etPhone.setText("")
        }
        mViewBinding.ivDeletePassword.setOnClickListener {
            mViewBinding.etPassword.setText("")
        }
        //为了方便调试，预填一些数据
        /*val userInfo = UserManager.getUserInfo()
        if (userInfo.phone.isNullOrEmpty()) {
            mViewBinding.etPhone.setText("18501231486")
        } else {
            mViewBinding.etPhone.setText(userInfo.phone)
        }
        mViewBinding.etPassword.setText("111111")*/

        mViewBinding.tvLogin.setOnClickListener {
            val phone = mViewBinding.etPhone.text.toString()
            val password = mViewBinding.etPassword.text.toString()
            when {
                phone.isEmpty() -> toast("手机号不能为空")
                !StringUtils.isMobilePhone(phone) -> toast("手机号格式错误")
                password.isEmpty() -> toast("密码不能为空")
                password.length != 6 -> toast("密码需要6位")
                else -> {
                    login(phone, password)
                }
            }
        }
        mViewBinding.tvRegister.setOnClickListener {
            RegisterActivity.startActivity(activity, RegisterActivity.Companion.IntentBean())
        }
    }

    fun login(phone: String, password: String) {
        mViewModel.login(phone, password).observe(this) {
            LogUtil.i(it.toString())
            if (it.isServerResultOK()) {
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }

    private fun initTitle() {
        mViewBinding.layoutTitle.ivBack.setOnClickListener {
            finish()
        }
        mViewBinding.layoutTitle.tvTitle.text = ""
    }
}