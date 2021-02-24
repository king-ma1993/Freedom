package com.myl.wanandroid.app.event

import com.myl.wanandroid.app.event.BaseEvent

class LoginFreshEvent(var login:Boolean,var collectIds:List<String>): BaseEvent()

