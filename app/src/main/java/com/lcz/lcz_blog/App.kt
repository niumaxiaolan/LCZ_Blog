package com.lcz.lcz_blog

import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.lcz.lcz_blog.callback.EmptyCallback
import com.lcz.lcz_blog.callback.ErrorCallback
import com.lcz.lcz_blog.callback.LoadingCallback
import com.lcz.lcz_blog.util.log.LogUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.*
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import kotlin.properties.Delegates

/**
 * @author 刘传政
 * @date 2021/9/13 0013 17:20
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
class App :Application() {
    companion object {
        //这写法可以获取时没初始化就报错。实际使用其实意义不大。
        var instance: Application by Delegates.notNull()
            private set

    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        LogUtil.init()
        initLoadSir()
        initSmartRefreshLayout()
    }
    fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            //.setDefaultCallback(LoadingCallback.class) //这样默认显示loading，但是如果你立马showsuccess，会没效果,因为源码中会延迟发送default。所以先不用
            .commit()
    }
    fun initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.common_theme, android.R.color.white) //全局设置主题颜色
            //是否启用列表惯性滑动到底部时自动加载更多
            layout.setEnableAutoLoadMore(true)
            layout.setEnableOverScrollDrag(true) //是否启用越界拖动（仿苹果效果）1.0.4
            layout.setEnableOverScrollBounce(true) //是否启用越界回弹
            //layout.setEnablePureScrollMode(true);//是否启用纯滚动模式.启用后使得没有下拉刷新和上拉加载效果.但是和ios的越界拖动很像了
//                layout.setEnableLoadMoreWhenContentNotFull(false);
            MaterialHeader(context) //google官方下拉效果。本项目中有个总是露个小黑边bug
            //                return new WaveSwipeHeader(context);//降落的水滴效果。
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }
}