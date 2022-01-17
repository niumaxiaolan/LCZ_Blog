package com.lcz.lcz_blog.module.mian.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.databinding.ActivityMainBinding
import com.lcz.lcz_blog.module.blog.fragment.BlogListFragment
import com.lcz.lcz_blog.module.mian.adapter.MyMainPagerAdapter
import com.lcz.lcz_blog.util.log.LogUtil
import com.liuchuanzheng.lcz_wanandroid.base.BaseViewModel

class MainActivity : BaseVMActivity<BaseViewModel>() {
    var fragmentList: ArrayList<Fragment> = ArrayList<Fragment>()
    private var titles: List<String> = listOf<String>("博客", "我的")
    val mViewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var animatorSet: AnimatorSet? = null
    override fun setContentView() {
        setContentView(mViewBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTabAndViewPager()
    }
    private fun setTabAndViewPager() {
        fragmentList.add(BlogListFragment())
        fragmentList.add(BlogListFragment())
        val myMainPagerAdapter = MyMainPagerAdapter(supportFragmentManager, fragmentList)
        mViewBinding.viewPager.setAdapter(myMainPagerAdapter)
        //设置不让滑动
        mViewBinding.viewPager.setScroll(false)
        mViewBinding.viewPager.setOffscreenPageLimit(fragmentList.size)
        //Viewpager的监听（这个接听是为Tablayout专门设计的）
        mViewBinding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mViewBinding.tabLayout))
        mViewBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                LogUtil.i("tab选择了$position")
                mViewBinding.viewPager.setCurrentItem(position, false)
                val customView = tab.customView
                if (customView != null) {
                    val tv_name = customView.findViewById<TextView>(R.id.tv_name)
                    val iv_image = customView.findViewById<ImageView>(R.id.iv_image)
                    tv_name.setTextColor(resources.getColor(R.color.common_theme))
                    if (0 == position) {
                        iv_image.background = resources.getDrawable(R.drawable.logo_select)
                        startAnimator(iv_image)
                    } else if (1 == position) {
                        iv_image.background = resources.getDrawable(R.drawable.tab_my_select)
                        startAnimator(iv_image)
                    }
                }
//                updateStatuBar()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val customView = tab.customView
                val position = tab.position
                if (customView != null) {
                    val tv_name = customView.findViewById<TextView>(R.id.tv_name)
                    val iv_image = customView.findViewById<ImageView>(R.id.iv_image)
                    tv_name.setTextColor(resources.getColor(R.color.common_text_unselect))
                    if (0 == position) {
                        iv_image.background = resources.getDrawable(R.drawable.tab_kuaiqicha_unselect)
                    }  else if (1 == position) {
                        iv_image.background = resources.getDrawable(R.drawable.tab_my_unselect)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        for (i in titles.indices) {
            val tab: TabLayout.Tab = mViewBinding.tabLayout.newTab()
            val customView: View = LayoutInflater.from(this).inflate(R.layout.tab_main_item, null)
            tab.customView = customView
            val tv_name = customView.findViewById<TextView>(R.id.tv_name)
            val iv_image = customView.findViewById<ImageView>(R.id.iv_image)
            tv_name.setText(titles.get(i))
            if (0 == i) {
                iv_image.background = resources.getDrawable(R.drawable.logo_select)
                tv_name.setTextColor(resources.getColor(R.color.common_theme))
            }  else if (1 == i) {
                iv_image.background = resources.getDrawable(R.drawable.tab_my_unselect)
                tv_name.setTextColor(resources.getColor(R.color.common_text_unselect))
            }
            mViewBinding.tabLayout.addTab(tab)
        }

    }

    private fun startAnimator(view: View) {
        // 动画
        if (animatorSet != null) {
            //之前有别的动画，先结束掉,而不是取消.取消的话会停留在动画中的某个位置
//            animatorSet.cancel();
            animatorSet!!.end()
        }
        animatorSet = AnimatorSet()
        val duration: Long = 300
        animatorSet!!.playTogether(
            ObjectAnimator.ofFloat(view, "scaleX", 0.5.toFloat(), 1f)
                .setDuration(duration),
            ObjectAnimator.ofFloat(view, "scaleY", 0.5.toFloat(), 1f)
                .setDuration(duration),
            ObjectAnimator.ofFloat(view, "alpha", 0.5.toFloat(), 1f)
                .setDuration(duration),
            ObjectAnimator.ofFloat(view, "Rotation", 180f, 360f)
                .setDuration(duration)
        )
        animatorSet!!.start()
    }
}