package com.alwin.applapangan.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alwin.applapangan.MainActivity
import com.alwin.applapangan.R
import com.alwin.applapangan.models.login.BodyLogin
import com.alwin.applapangan.models.register.BodyRegister
import com.alwin.applapangan.ui.login.LoginActivity
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponseOther
import com.driver.nyaku.ui.BaseActivity
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.edt_email
import kotlinx.android.synthetic.main.activity_register.edt_password
import kotlinx.android.synthetic.main.main_header.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getSupportActionBar()?.hide();
        tv_login.setOnClickListener {
            intentTo(LoginActivity::class.java)
            finish()
        }

        img_back.setOnClickListener {   intentTo(LoginActivity::class.java)
            finish() }

        btn_register.setOnClickListener { 
            if (edt_name.text.toString().isNullOrEmpty()){
                showErrorMessage("Nama tidak bisa kosong")
            }else if (edt_password.text.toString().isNullOrEmpty()){
                showErrorMessage("Password tidak bisa kosong")
            }else if (edt_email.text.toString().isNullOrEmpty()){
                showErrorMessage("Email tidak bisa kosong")
            } else {
                postRegister()
            }
        }

    }

    private fun postRegister() {
        val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Prefuser().getToken(),
            Constant.PASS
        )

        showLoading(this)
        val body = BodyRegister(
            edt_password.text.toString(),
            "user",
            edt_name.text.toString(),
            edt_email.text.toString(),
           
        )
        
        api.postRegister(body).enqueue(object : Callback<BaseResponseOther>{
            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                hideLoading()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        showSuccessMessage("Register Berhasil Sukses")
                        intentTo(LoginActivity::class.java)
                        finish()

                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        showErrorMessage("Gagal Register,${jObjError.getString("message")}")

                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    showErrorMessage("Gagal Register,${jObjError.getString("message")}")

                }
            }

            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                hideLoading()
            }
        })
    }
}