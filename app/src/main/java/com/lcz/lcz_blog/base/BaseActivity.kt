package com.liuchuanzheng.lcz_wanandroid.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.base.BaseIntentBean
import com.lcz.lcz_blog.util.dialog.DialogUtil
import com.lcz.lcz_blog.util.log.LogUtil


/**
 * @author 刘传政
 * @date 2021/9/13 0013 18:13
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
abstract class BaseActivity : AppCompatActivity(), ILoading {
    lateinit var context: Context
    lateinit var activity: Activity
    private lateinit var loadingDialog: DialogUtil
    //子类想做事情就复写此方法
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        activity = this
        loadingDialog = DialogUtil(activity)
        //因为大部分界面在没有特意适配横屏时，自动适配效果根本不理想，所以直接强制竖屏，有特殊需要自己再设置
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView()
    }

    /**
     * 因为系统自带的设置view方法有很多种,id,view都可以.所以这里不像
     * 大家都用id的封装方式,那是限制了自己
     * 建议用viewBinding的方式。个人不喜欢dataBinding
     */
    protected abstract fun setContentView()

    /**
     * 解析传递的固定格式的bean。这样不用一个一个参数的传。而且需要修改参数时方便提示
     * @return T?
     */
    fun <T : BaseIntentBean> parseIntent(): T? {
        //获取参数
        val bean: T? = getIntent().getSerializableExtra(Constant.IntentKey.IntentBean) as T?
        if (bean != null) {
            LogUtil.i("传递过来的参数:$bean")
            return bean as T
        } else {
            LogUtil.i("没传过来参数")
            return null
        }
    }

    override fun showLoading(msg: String) {
        loadingDialog.showLoading(msg)
    }

    override fun hideLoading() {
        loadingDialog.dismissLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
    }

}