package com.alwin.applapangan.ui

import android.app.Activity

interface Baseview {

    fun showErrorMessage(message:String?)
    fun showSuccessMessage(message:String?)
    fun showInfoMessage(message:String?)
    fun showLongErrorMessage(message:String?)
    fun showLongSuccessMessage(message:String?)
    fun showLongInfoMessage(message:String?)
    fun showProgressDialog()
    fun showProgressDialog(message:String?)
    fun updateMessageDialog(message:String?)
    fun dismissProgressDialog()
    fun closeActivity()
    fun intentTo(target:Class<*>)
    fun refresh()
    fun showLoading(context : Activity)
    fun hideLoading()
}