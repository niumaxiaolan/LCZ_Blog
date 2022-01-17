package com.lcz.lcz_blog.callback;

import com.kingja.loadsir.callback.Callback;
import com.lcz.lcz_blog.R;


public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
