package com.lcz.lcz_blog.util.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.ConvertUtils;
import com.lcz.lcz_blog.R;


/**
 * 加载提醒对话框
 */
public class CustomDialog extends ProgressDialog {
    public TextView tv_tips;
    public LottieAnimationView animation_view;
    private View view;

    public CustomDialog(Context context) {
        this(context, R.style.myDialog);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(true);
        setCanceledOnTouchOutside(false);

//        setContentView(R.layout.common_loading_view);
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.common_loading_view, null);
        tv_tips = view.findViewById(R.id.tv_tips);
        animation_view = view.findViewById(R.id.animation_view);
        setContentView(view);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        //设置大小.否则默认的大小太大
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ConvertUtils.dp2px(150);
        params.height = ConvertUtils.dp2px(150);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public void show() {
        super.show();
    }

    public void setMessage(String message) {
        if (tv_tips == null) {
            tv_tips = view.findViewById(R.id.tv_tips);
        }
        tv_tips.setText(message);
    }

    public void startAnimation() {
        if (animation_view == null) {
            animation_view = view.findViewById(R.id.animation_view);
        }
        animation_view.playAnimation();

    }

    public void stopAnimation() {
        if (animation_view == null) {
            animation_view = view.findViewById(R.id.animation_view);
        }
        animation_view.cancelAnimation();

    }
}