package com.lcz.lcz_blog.module.mian.activity

import android.os.Bundle
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.base.BaseVMActivity
import com.liuchuanzheng.lcz_wanandroid.base.BaseViewModel

class MainActivity : BaseVMActivity<BaseViewModel>() {
    override fun setContentView() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}