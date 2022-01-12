package com.lcz.lcz_blog.util.dialog;

import android.app.Activity;

/**
 * @author 刘传政
 * @date 2020/3/11 15:45
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */
public class DialogUtil {
    private  CustomDialog progressDialog;
    private  Activity activity;

    public DialogUtil(Activity activity) {
        this.activity = activity;
        progressDialog = new CustomDialog(activity);
    }

    public  void showLoading(String msg) {
        progressDialog.show();
        progressDialog.setMessage(msg);
        progressDialog.startAnimation();
    }

    public  void dismissLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.stopAnimation();
            progressDialog.dismiss();
        }

    }
}
