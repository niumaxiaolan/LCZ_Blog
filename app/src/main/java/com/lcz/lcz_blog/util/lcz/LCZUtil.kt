package com.liuchuanzheng.baselib.util.lcz

import android.content.res.Resources
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import com.lcz.lcz_blog.App
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author 刘传政
 * @date 2021/6/10 11:15
 * QQ:1052374416
 * 电话:18501231486
 * 作用:这里边是我常用的习惯的工具类.不一定是全的,好的,但是是我最习惯的
 * 而且所有工具类都在这个文件中.想分类也能在这里写.
 * 注意事项:
 */
object LCZUtil {
    //............................................普通工具方法........................................................
    //java调用 LCZUtil.dp2px(10f);
    //kotlin调用 LCZUtil.dp2px(10f)


    //加了这个注解便于java直接调用.否则要LCZUtil.INSTANCE.dp2px();
    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        //0.5是为了四舍五入
        /**
         * 加上0.5f是因为 咱们要的只不是那么精准，根据推理算出来的是个浮点数，而咱们程序中一般使用int类型就够了，这里涉及到一个类型转换精准度问题，熟悉java特效的同学应该知道

        float 类型的 4.1 和4.9 强转成int类型后，会失去精准度变成 int类型的4， 而如果咱们想四舍五入的话，把他们都加上0.5f，这样转换出来的结果就是：

        4.4 + 0.5 = 4.9 转为int 还是4，而4.5 + 0.5 = 5.0 转换成int后就是5，正好是四舍五入，这样就保证了咱们算出来的值相对精准。
        ————————————————
        版权声明：本文为CSDN博主「changcsw」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/changcsw/article/details/52440543
         */
        return (dpValue * scale + 0.5f).toInt()
    }

    @JvmStatic
    fun px2dp(pxValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     *  关键字高亮显示
     *  text  原文
     *  keyWord 需要高亮显示的关键字
     *  isCut 是否需要做分词高亮展示
     *  isCut = true  关键字里的每一个字，只要有都会高亮
     *  isCut = false（默认） 只有整词才会高亮
     **/
    @JvmStatic
    fun stringToHighLight(
        text: String,
        keyWord: String,
        isCut: Boolean = false,
        hightLightColor: String = "#4599F7"
    ): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)
        try {
            var keywordList: MutableList<String> = ArrayList()
            if (isCut) {
                for (i in keyWord.indices) {
                    keywordList.add(keyWord.substring(i, i + 1))
                }
            } else {
                keywordList = arrayListOf(keyWord)
            }
            var span: CharacterStyle?
            var wordReg: String
            for (i in keywordList.indices) {
                var key = ""
                if (keywordList[i].contains("*") || keywordList[i].contains("(") || keywordList[i].contains(
                        ")"
                    )
                ) {
                    val chars = keywordList[i].toCharArray()
                    for (k in chars.indices) {
                        key = if (chars[k] == '*' || chars[k] == '(' || chars[k] == ')') {
                            key + "\\" + chars[k].toString()
                        } else {
                            key + chars[k].toString()
                        }
                    }
                    keywordList[i] = key
                }
                wordReg = "(?i)" + keywordList[i]
                val pattern: Pattern = Pattern.compile(wordReg)
                val matcher: Matcher = pattern.matcher(text)
                while (matcher.find()) {
                    //设置高亮颜色
                    span = ForegroundColorSpan(Color.parseColor(hightLightColor))
                    spannable.setSpan(
                        span,
                        matcher.start(),
                        matcher.end(),
                        Spannable.SPAN_MARK_MARK
                    )
                }
            }
        } catch (e: Exception) {
            Log.d("LCZUtil","stringToHighLight-Error-------->$e")
        }
        return spannable
    }


    //............................................普通工具方法又分大类........................................................
    //java调用 LCZUtil.TestUtil.test();
    //kotlin调用 LCZUtil.TestUtil.test()
    object TestUtil {
        @JvmStatic
        fun test() {
            //这只是举例子,验证能分大类
        }
    }


    //............................................kotlin扩展工具方法 java调用会啰嗦一点,所以尽量只在kotlin中使用........................................................
    //java调用 String str = LCZUtil.str(textView);
    //kotlin调用 var str = textView.str()


    /**
     * textview获取trim后的文字
     */
    @JvmStatic
    fun TextView.str(): String {
        return this.text.toString().trim()
    }
}

//顶级方法
fun toast(message: CharSequence) {
    Toast.makeText(App.instance, message, Toast.LENGTH_SHORT).show()
}

fun toast(@StringRes message: Int) {
    Toast.makeText(App.instance, message, Toast.LENGTH_SHORT).show()
}


