package com.alwin.applapangan.ui.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.BodyBooking
import com.alwin.applapangan.models.jadwal.ResponseJadwal
import com.alwin.applapangan.models.lapangan.ResponseLapangan
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponseOther
import com.driver.nyaku.ui.BaseActivity
import com.driver.nyaku.utils.currencyFormatter
import com.driver.nyaku.utils.invisible
import com.google.gson.Gson
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_detail_lapangan.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailShowLapanganActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapangan)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val product = intent.getParcelableExtra<ResponseLapangan>(ResponseLapangan::class.simpleName)
        tv_name.text = "Nama Lapangan : "+product?.namaLapangan
        tv_price.text = "Harga perjam : " + currencyFormatter(product?.harga.toString())
        tv_date.invisible()

        tv_gedung.text =
            "Lokasi Lapangan berada pada gedung : ${product?.gedung?.namaGedung} - Dapat menghubungi pemilik :  ${product?.gedung?.kontakPemilik}"
        tv_deskripsi.text =
            "Alamat Lapangan : ${product?.gedung?.alamat} - ${product?.gedung?.provinsi}, ${product?.gedung?.kabupaten}, ${product?.gedung?.kecamatan}, Desa ${product?.gedung?.desa}"


        btn_booking.invisible()
        btn_booking.setOnClickListener {
            val api = ServiceGenerator.createService(
                ApiInterface::class.java,
                Prefuser().getToken(),
                Constant.PASS
            )

            showLoading(this)

            val id = product?.id?.toInt()

            val body = BodyBooking(id, "baru")
            api.postBooking(body).enqueue(object : Callback<BaseResponseOther> {
                override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                    hideLoading()
                    if (response.isSuccessful) {

                        if (response.body()?.status == true) {
                            showLongSuccessMessage("Berhasil Booking")
                            finish()
                        } else {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            showErrorMessage("Gagal dapatakan data ,${jObjError.getString("message")}")

                        }
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        showErrorMessage("Gagal dapatakan data ,${jObjError.getString("message")}")

                    }
                }

                override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                    showErrorMessage("Gagal, Periksa jaringan internet")
                }
            })


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}