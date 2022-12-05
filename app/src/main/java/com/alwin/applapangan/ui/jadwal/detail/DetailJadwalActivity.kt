package com.alwin.applapangan.ui.jadwal.detail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.BodyBookingNew
import com.alwin.applapangan.models.gedung.JadwalsItem
import com.alwin.applapangan.models.jadwal.BodyCekJadwal
import com.alwin.applapangan.service.NotificationManager
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponseOther
import com.driver.nyaku.ui.BaseActivity
import com.driver.nyaku.utils.invisible
import com.driver.nyaku.utils.visible
import com.google.gson.Gson
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_detail_jadwal.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailJadwalActivity : BaseActivity(), AdapterDetailJadwalForBooking.OnListener {
    var adapterDetailJadwalForBooking: AdapterDetailJadwalForBooking? = null
    var spinnerMulai: Spinner? = null
    var spinnerSelesai: Spinner? = null
    var jamMulai = 0
    var jamSelesai = 0
    var data: JadwalsItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jadwal)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        data = intent.getParcelableExtra<JadwalsItem>(JadwalsItem::class.java.name)

        val dataMulai = "${data?.mulai?.substring(0, 2)}"
        val dataSelesai = "${data?.selesai?.substring(0, 2)}"
        val dataAdapter: MutableList<String> = mutableListOf<String>()
        Log.e("Data", "$dataMulai $dataSelesai")
        for (data in dataMulai.toInt()..dataSelesai.toInt()) {
            dataAdapter.add(data.toString())
        }

        adapterDetailJadwalForBooking = AdapterDetailJadwalForBooking(this, dataAdapter, this)
        rv_jadwal.adapter = adapterDetailJadwalForBooking
        rv_jadwal.layoutManager = GridLayoutManager(this, 2)
        adapterDetailJadwalForBooking?.notifyDataSetChanged()

        spinnerMulai = spn_mulai
        spinnerSelesai = spn_selesai

        val mulai = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataAdapter)
        val selesai = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataAdapter)
        // Set layout to use when the list of choices appear
        mulai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selesai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinnerMulai?.setAdapter(mulai)
        spinnerSelesai?.setAdapter(mulai)

        spinnerMulai?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                jamMulai = dataAdapter[position]?.toInt()
                btn_booking.invisible()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        spinnerSelesai?.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                jamSelesai = dataAdapter[position]?.toInt()
                btn_booking.invisible()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        btn_booking.setOnClickListener {
            bookingJadwal(jamMulai, jamSelesai)
        }
        btn_cek.setOnClickListener {
            if (jamSelesai <= jamMulai) {
                showErrorMessage("Jam Selesai tidak bisa lebih kecil dari jam mulai")
            } else
                cekJadwal(jamMulai, jamSelesai)
        }

    }

    private fun bookingJadwal(jamMulai: Int, jamSelesai: Int) {
        val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Prefuser().getToken(),
            Constant.PASS
        )
        showLoading(this)
        val dataId: ArrayList<Int> = ArrayList()
        val id = data?.id?.toInt()

        dataId.add(id.toString().toInt())
        val body = BodyBookingNew(jamSelesai.toString() + ":00", jamMulai.toString() + ":00", dataId, "baru")
        Log.e("TAG", "${Gson().toJson(body)}")
        api.postBookingNew(body).enqueue(object : Callback<BaseResponseOther> {
            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {

                hideLoading()
                if (response.isSuccessful) {
                    if (response.body()?.status?.equals(true) == true) {
                        finish()
                        showLongSuccessMessage("Sukses Booking")
                        NotificationManager(this@DetailJadwalActivity).displayNotification(
                            "Berhasil Booking",
                            "Selamat anda berhasil booking jadwal",
                            "",
                            ""
                        )
                    }  else {
                        showInfoMessage("Gagal di Booking")
                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    showErrorMessage("Gagal,${jObjError.getString("message")}")

                }
            }

            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                hideLoading()
                showErrorMessage("Periksa Internet")
            }
        })
    }

    private fun cekJadwal(jamMulai: Int, jamSelesai: Int) {
        val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Prefuser().getToken(),
            Constant.PASS
        )
        showLoading(this)

        val body = BodyCekJadwal(jamSelesai.toString()+":00", data?.id.toString(), jamMulai.toString()+":00")
        api.postCekJadwal(body).enqueue(object : Callback<BaseResponseOther> {
            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                hideLoading()
                if (response.isSuccessful) {
                    if (response.body()?.status == true) {
                        showSuccessMessage("Jam tersedia")
                        btn_booking.visible()
                    } else {
                        showErrorMessage("Jam tidak tersedia")
                    }
                } else {
                    showErrorMessage("Jam tidak tersedia")
                }
            }

            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
                showErrorMessage("Gagal dapatkan data")
                hideLoading()
            }
        })
    }


    override fun onClickGrup(data: String) {

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