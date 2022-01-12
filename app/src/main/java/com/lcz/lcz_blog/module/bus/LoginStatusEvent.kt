package com.lcz.lcz_blog.module.bus

import com.jeremyliao.liveeventbus.core.LiveEvent

class LoginStatusEvent : LiveEvent {
    var isLogin = false
}