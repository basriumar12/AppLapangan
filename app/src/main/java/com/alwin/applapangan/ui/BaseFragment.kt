package com.driver.nyaku.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alwin.applapangan.ui.Baseview
import com.orhanobut.hawk.Hawk
import com.valdesekamdem.library.mdtoast.MDToast


open class BaseFragment : Fragment(), Baseview {
    lateinit var loading: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading = ProgressDialog(activity)
        Hawk.init(context).build();


    }
    override fun onDestroy() {
        super.onDestroy()
        if ( loading!=null && loading.isShowing() ){
            loading.cancel();
        }
    }
    override fun showErrorMessage(message: String?) {
        MDToast.makeText(context,message, MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show()
    }

    override fun showSuccessMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show()
    }

    override fun showInfoMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_LONG,MDToast.TYPE_INFO).show()
    }

    override fun showLongErrorMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show()
    }

    override fun showLongSuccessMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show()
    }

    override fun showLongInfoMessage(message: String?) {
        MDToast.makeText(context,message,MDToast.LENGTH_LONG,MDToast.TYPE_INFO).show()
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

    }

    override fun refresh() {
        onResume()
    }

    override fun showLoading(context: Activity) {

        loading.setTitle("Loading")
        loading.show()

        loading.setCancelable(false)
    }

    override fun hideLoading() {
        loading.dismiss()
    }


    override fun intentTo(target: Class<*>) {
        context?.startActivity(Intent(context, target))
    }

}