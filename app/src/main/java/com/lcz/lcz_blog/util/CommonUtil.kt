package com.lcz.lcz_blog.util

import android.app.Activity
import android.view.WindowManager
import android.widget.EditText

/**
 * @author 刘传政
 * @date 2021/6/4 10:46
 * QQ:1052374416
 * 电话:18501231486
 * 作用:
 * 注意事项:
 */

/**
 * EditText获取焦点并显示软键盘
 */
fun showKeyboard(activity: Activity, editText: EditText) {
    editText.isFocusable = true
    editText.isFocusableInTouchMode = true
    editText.requestFocus()
    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}