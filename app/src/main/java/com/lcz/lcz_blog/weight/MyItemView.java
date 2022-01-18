package com.lcz.lcz_blog.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcz.lcz_blog.R;


public class MyItemView extends RelativeLayout {

    TextView tv_title;
    ImageView iv_icon;
    CharSequence titleText = "";

    public MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyItemView);
        int title = ta.getResourceId(R.styleable.MyItemView_MyItemView_title, 0);
        titleText = ta.getText(R.styleable.MyItemView_MyItemView_title);
        final Drawable d = ta.getDrawable(R.styleable.MyItemView_MyItemView_icon);


        if (ta != null) {
            ta.recycle();
        }


        LayoutInflater.from(context).inflate(R.layout.my_item_view, this, true);

        tv_title = findViewById(R.id.tv_title);
        iv_icon = findViewById(R.id.iv_icon);

        if (d != null) {
            iv_icon.setImageDrawable(d);
        }
        if (title != 0) {
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(titleText)) {
            tv_title.setText(titleText);
        }

    }

    public TextView getTitleView() {
        return tv_title;
    }


}
