package com.lcz.lcz_blog.module.user.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.databinding.ActivityChangeUsernameBinding
import com.lcz.lcz_blog.module.bus.UpdataUserInfoEvent
import com.lcz.lcz_blog.module.user.viewmodel.ChangeUserNameViewModel
import com.lcz.lcz_blog.store.UserManager
import com.liuchuanzheng.baselib.util.lcz.toast

class ChangeUserNameActivity : BaseVMActivity<ChangeUserNameViewModel>() {
    val mViewBinding by lazy { ActivityChangeUsernameBinding.inflate(layoutInflater) }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ChangeUserNameActivity::class.java)
            context.startActivity(intent)
        }

    }


    override fun setContentView() {
        setContentView(mViewBinding.root)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTitle()
        mViewBinding.etUsername.setText(UserManager.getUserInfo().username)
        mViewBinding.tvSave.setOnClickListener {
            var trim = mViewBinding.etUsername.text.toString().trim()
            if (trim.isNullOrEmpty()) {
                toast("用户名不能为空")
            } else {
                net_changeUsername(trim)
            }
        }
    }

    fun net_changeUsername(trim: String) {
        mViewModel.changeUsername(trim).observe(this) {
            if (it.isServerResultOK()) {
                toast("修改成功")
                UpdataUserInfoEvent().apply {
                    LiveEventBus.get(UpdataUserInfoEvent::class.java).post(this)
                }
                finishAfterTransition()
            } else {
                toast("修改失败")
            }
        }
    }


    private fun initTitle() {
        mViewBinding.layoutTitle.ivBack.setOnClickListener {
            finishAfterTransition()
        }
        mViewBinding.layoutTitle.tvTitle.text = "修改用户名"
    }


}