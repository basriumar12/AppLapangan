package com.gorontalodigital.preference

import com.alwin.applapangan.utils.Constant
import com.orhanobut.hawk.Hawk

class Prefuser {
    fun setUser(user: String?) = Hawk.put(Constant.user, user)

    fun getUser(): String? = Hawk.get(Constant.user, null)


    fun setToken(user: String?) = Hawk.put(Constant.token, user)

    fun getToken(): String? = Hawk.get(Constant.token, null)


}