package com.alwin.applapangan.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alwin.applapangan.MainActivity
import com.alwin.applapangan.R
import com.alwin.applapangan.models.login.BodyLogin
import com.alwin.applapangan.ui.register.RegisterActivity
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponse
import com.driver.nyaku.models.BaseResponseOther
import com.driver.nyaku.ui.BaseActivity
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.main_header.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getSupportActionBar()?.hide();
        tv_daftar.setOnClickListener {
            intentTo(RegisterActivity::class.java)
            finish()
        }

        img_back.setOnClickListener {
            finish()
        }
        btn_login.setOnClickListener {
            if (edt_email.text.toString().isNullOrEmpty()) {
                showErrorMessage("Email tidak bisa kosong")
            } else if (edt_password.text.toString().isNullOrEmpty()) {
                showErrorMessage("Password tidak bisa kosong")
            } else {
                postLogin()
            }
        }
    }

    private fun postLogin() {
        val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Prefuser().getToken(),
            Constant.PASS
        )

        showLoading(this)
        val body = BodyLogin(
            edt_password.text.toString(),
            edt_email.text.toString()
        )
        api.postLogin(body).enqueue(object : Callback<BaseResponse<String>> {
            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                hideLoading()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        showSuccessMessage("Login Sukses")
                        intentTo(MainActivity::class.java)
                        Prefuser().setToken(response.body()?.data)
                        finish()

                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        showErrorMessage("Gagal Login,${jObjError.getString("message")}")

                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    showErrorMessage("Gagal Login,${jObjError.getString("message")}")

                }
            }

            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                hideLoading()
                showErrorMessage("Periksa Jaringan Internet")
            }
        })
    }
}