package com.alwin.applapangan.ui.jadwal

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.BodyBooking
import com.alwin.applapangan.models.jadwal.ResponseJadwal
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponseOther
import com.driver.nyaku.ui.BaseActivity
import com.driver.nyaku.utils.currencyFormatter
import com.google.gson.Gson
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_detail_lapangan.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLapanganActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapangan)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val product = intent.getParcelableExtra<ResponseJadwal>(ResponseJadwal::class.simpleName)
        Log.e("TAG", "${Gson().toJson(product)}")
        tv_name.text = product?.lapangan?.namaLapangan
        tv_price.text = "Harga perjam : " + currencyFormatter(product?.lapangan?.harga.toString())
        tv_date.text = "Jadwal Buka ${product?.tanggal} / Jam ${product?.mulai} -  ${product?.selesai}"
        tv_gedung.text =
            "Lokasi Lapangan berada pada gedung : ${product?.lapangan?.gedung?.namaGedung} - Bisa menghubungi Pemilik :  ${product?.lapangan?.gedung?.kontakPemilik}"
        tv_deskripsi.text =
            "Alamat Lapangan : ${product?.lapangan?.gedung?.alamat} - ${product?.lapangan?.gedung?.provinsi}, ${product?.lapangan?.gedung?.kabupaten}, ${product?.lapangan?.gedung?.kecamatan}"


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