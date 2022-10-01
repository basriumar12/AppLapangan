package com.driver.nyaku.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.alwin.applapangan.ui.Baseview
import com.orhanobut.hawk.Hawk
import com.valdesekamdem.library.mdtoast.MDToast

open class BaseActivity : AppCompatActivity(), Baseview {
    lateinit var loading: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        loading = ProgressDialog(this)
        Hawk.init(this).build();

    }

    override fun showErrorMessage(message: String?) {
        MDToast.makeText(this, message, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show()
    }

    override fun showSuccessMessage(message: String?) {
        MDToast.makeText(this, message, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show()
    }

    override fun showInfoMessage(message: String?) {
        MDToast.makeText(this, message, MDToast.LENGTH_LONG, MDToast.TYPE_INFO).show()
    }

    override fun showLongErrorMessage(message: String?) {
        MDToast.makeText(this, message, MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show()
    }

    override fun showLongSuccessMessage(message: String?) {
        MDToast.makeText(this, message, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show()
    }

    override fun showLongInfoMessage(message: String?) {
        MDToast.makeText(this, message, MDToast.LENGTH_LONG, MDToast.TYPE_INFO).show()
    }

    override fun showProgressDialog() {

    }

    override fun showProgressDialog(message: String?) {

    }

    override fun updateMessageDialog(message: String?) {

    }

    override fun dismissProgressDialog() {

    }


    override fun closeActivity() {
        this.finish()
    }

    override fun refresh() {
        onResume()
    }

    override fun showLoading(context: Activity) {
        try {
            loading.setTitle("Loading")
            loading?.show()
            loading.setCancelable(false)
        } catch (i: IllegalArgumentException) {
        }
    }

    override fun hideLoading() {
        loading.dismiss()
    }


    override fun onDestroy() {
        super.onDestroy()
        if ( loading!=null && loading.isShowing() ){
            loading.cancel();
        }
    }
    override fun intentTo(target: Class<*>) {
        this.startActivity(Intent(this, target))
    }

}