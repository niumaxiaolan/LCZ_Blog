package com.lcz.lcz_blog.module.splash.activity

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.annotation.NonNull
import com.dele.kuaiqicha.base.store.AppManager
import com.lcz.lcz_blog.module.mian.activity.MainActivity
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.databinding.ActivitySplashBinding
import com.liuchuanzheng.lcz_wanandroid.base.BaseActivity

/**
 * <pre>
 * <img width="128" height="110" src="https://tse3-mm.cn.bing.net/th/id/OIP-C.en-w_oH-yn2UsSRfWnOsGAHaGY?w=198&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7" alt="马刺">
 * 作者:     <a href="https://github.com/liuchuanzheng">刘传政</a>
 * 创建时间:  10:24 2022/1/12 0012
 * QQ:      1052374416
 * 电话:     18501231486
 * 描述:
 * </pre>
 */
class SplashActivity : BaseActivity() {
    val mViewBinding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun setContentView() {
        setContentView(mViewBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding.apply {
            animationView2.loop(false)

            btnGo.setOnClickListener {
                startAnimal2()
            }
            animationView2.addAnimatorListener(object : Animator.AnimatorListener {
                /**
                 *
                 * Notifies the start of the animation.
                 *
                 * @param animation The started animation.
                 */
                override fun onAnimationStart(animation: Animator?) {

                }

                /**
                 *
                 * Notifies the end of the animation. This callback is not invoked
                 * for animations with repeat count set to INFINITE.
                 *
                 * @param animation The animation which reached its end.
                 */
                override fun onAnimationEnd(animation: Animator?) {
                    AppManager.saveSplashShowedStatus(true)
                    finish()
                    AppManager.analyseGoToMain(this@SplashActivity)
                }

                /**
                 *
                 * Notifies the cancellation of the animation. This callback is not invoked
                 * for animations with repeat count set to INFINITE.
                 *
                 * @param animation The animation which was canceled.
                 */
                override fun onAnimationCancel(animation: Animator?) {

                }

                /**
                 *
                 * Notifies the repetition of the animation.
                 *
                 * @param animation The animation which was repeated.
                 */
                override fun onAnimationRepeat(animation: Animator?) {

                }

            })

            if (AppManager.getSplashShowedStatus()) {
                //展示过一次。就简化闪屏
                startAnimal2()
            }
        }

        //协程的写法。
//        GlobalScope.launch {
//            delay(2000)
//            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//            finish()
//        }

    }
    private fun startAnimal2() {
        mViewBinding.apply {
            animationView.visibility = View.GONE
            animationView2.visibility = View.VISIBLE
            animationView2.playAnimation()
            btnGo.visibility = View.GONE
        }

    }

}