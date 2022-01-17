package com.lcz.lcz_blog.callback;

import android.content.Context;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.kingja.loadsir.callback.Callback;
import com.lcz.lcz_blog.R;


public class LoadingCallback extends Callback {
    LottieAnimationView animation_view;

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }


    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }

    //将Callback添加到当前视图时的回调，View为当前Callback的布局View
    @Override
    public void onAttach(Context context, View view) {
        super.onAttach(context, view);
        animation_view = view.findViewById(R.id.animation_view);
        animation_view.playAnimation();
    }

    //将Callback从当前视图删除时的回调，View为当前Callback的布局View
    @Override
    public void onDetach() {
        super.onDetach();
        animation_view.cancelAnimation();
    }
}
