package com.lcz.lcz_blog.module.user.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lcz.lcz_blog.R
import com.lcz.lcz_blog.base.BaseVMActivity
import com.lcz.lcz_blog.databinding.ActivityChangeHeadBinding
import com.lcz.lcz_blog.module.bus.UpdataUserInfoEvent
import com.lcz.lcz_blog.module.user.viewmodel.ChangeIconViewModel
import com.lcz.lcz_blog.store.UserManager
import com.lcz.lcz_blog.util.GlideUtil
import com.liuchuanzheng.baselib.util.lcz.toast

class ChangeIconActivity : BaseVMActivity<ChangeIconViewModel>() {
    val mViewBinding by lazy { ActivityChangeHeadBinding.inflate(layoutInflater) }
    val adapter: MyAdapter by lazy { MyAdapter(null) }
    var checkedUrl = ""

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, ChangeIconActivity::class.java)
            context.startActivity(intent)
        }

        /**
         * view是需要共享效果的view
         * 否则不会报错,但没有动画效果
         */
        fun startActivityForTransition(activity: Activity, view: View) {
            //共享元素跳转
            val i = Intent(activity, ChangeIconActivity::class.java)
            val pair1 = androidx.core.util.Pair<View, String>(view, "SHARE_VIEW_1")
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1)
            // ActivityCompat是android支持库中用来适应不同android版本的
            ActivityCompat.startActivity(activity, i, optionsCompat.toBundle())
        }
    }


    override fun setContentView() {
        setContentView(mViewBinding.root)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTitle()
        var initIconUrls = initIconUrls()
        var currentIconUrl = UserManager.getUserInfo().icon
        checkedUrl = currentIconUrl
        GlideUtil.loadHead(context, currentIconUrl, mViewBinding.ivCurrentIcon)
        initIconUrls.forEach {
            if (it.url.equals(currentIconUrl)) {
                it.checked = true
            }
        }
        mViewBinding.recyclerView.setLayoutManager(GridLayoutManager(activity, 2))
        mViewBinding.recyclerView.adapter = adapter
        adapter.setNewInstance(initIconUrls)
        adapter.setOnItemClickListener { adapter, view, position ->
            initIconUrls.forEachIndexed { index, iconUrl ->
                if (iconUrl.checked && index != position) {
                    iconUrl.checked = false
                    adapter.notifyItemChanged(index)
                }

            }
            initIconUrls[position].checked = true
            adapter.notifyItemChanged(position)
            GlideUtil.loadHead(context, initIconUrls[position].url, mViewBinding.ivCurrentIcon)
            checkedUrl = initIconUrls[position].url

        }
        mViewBinding.tvPreview.setOnClickListener {
            var trim = mViewBinding.etCustomUrl.text.toString().trim()
            if (trim.isNullOrEmpty()) {
                toast("请输入图片链接")
            } else {
                GlideUtil.loadHead(context, trim, mViewBinding.ivCurrentIcon)
                checkedUrl = trim
            }
        }
        mViewBinding.tvSave.setOnClickListener {
            net_changeIconUrl()
        }
    }

    fun net_changeIconUrl() {
        mViewModel.changeIcon(checkedUrl).observe(this) {
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

    private fun initIconUrls(): MutableList<IconUrl> {
        //这里模拟头像链接了。实际项目应该使用阿里云对象存储sdk，上传图片生成图片链接
        var mutableListOf = mutableListOf<IconUrl>()
        mutableListOf.apply {
            add(IconUrl("http://m.imeitou.com/uploads/allimg/210526/3-210526154U9.jpg"))
            add(IconUrl("http://m.imeitou.com/uploads/allimg/210526/3-210526154Z0-50.jpg"))
            add(IconUrl("http://m.imeitou.com/uploads/allimg/210526/3-210526154U7-50.jpg"))
            add(IconUrl("http://m.imeitou.com/uploads/allimg/210526/3-210526154T6.jpg"))
            add(IconUrl("http://m.imeitou.com/uploads/allimg/210104/3-210104115003.jpg"))
            add(IconUrl("https://tse1-mm.cn.bing.net/th/id/OIP-C.xt7tfqek4NSrM1JXdsf1SgHaEo?w=275&h=180&c=7&r=0&o=5&dpr=1.12&pid=1.7"))
            add(IconUrl("https://tse1-mm.cn.bing.net/th/id/R-C.fc47ef1e33e832fcf2559461bb70b191?rik=nP6PSB%2bsDv5R6g&riu=http%3a%2f%2f5b0988e595225.cdn.sohucs.com%2fimages%2f20190919%2f4b26ea9c8b8540268d742f0a4197608b.jpeg&ehk=wk5%2fwnvk%2fZ9EetOn9t2wpqK67npkXD8rhX8N4WHi8as%3d&risl=&pid=ImgRaw&r=0"))
            add(IconUrl("https://tse4-mm.cn.bing.net/th/id/OIP-C.TZ8WGJzqyvh0u2yTXSmM7QHaNK?pid=ImgDet&rs=1"))
        }
        return mutableListOf
    }

    private fun initTitle() {
        mViewBinding.layoutTitle.ivBack.setOnClickListener {
            finishAfterTransition()
        }
        mViewBinding.layoutTitle.tvTitle.text = "修改头像"
    }


    inner class MyAdapter(
        data: MutableList<IconUrl>?
    ) :
        BaseQuickAdapter<IconUrl, BaseViewHolder>(
            R.layout.rv_item_change_icon,
            data
        ) {
        override fun convert(holder: BaseViewHolder, item: IconUrl) {
            var iv_icon = holder.getView<ImageView>(R.id.iv_icon)
            var iv_check_status = holder.getView<ImageView>(R.id.iv_check_status)
            GlideUtil.loadHead(context, item.url, iv_icon)
            if (item.checked) {
                iv_check_status.setImageResource(R.drawable.ic_checked)
            } else {
                iv_check_status.setImageResource(R.drawable.ic_uncheck)
            }
        }
    }

    data class IconUrl(var url: String, var checked: Boolean = false)
}