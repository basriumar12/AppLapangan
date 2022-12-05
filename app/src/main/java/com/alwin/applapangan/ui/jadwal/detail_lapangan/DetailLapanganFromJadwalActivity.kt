package com.alwin.applapangan.ui.jadwal.detail_lapangan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.BodyBooking
import com.alwin.applapangan.models.gedung.JadwalsItem
import com.alwin.applapangan.models.gedung.LapangansItem
import com.alwin.applapangan.ui.jadwal.detail.DetailJadwalActivity
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.AppConstant
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.driver.nyaku.models.BaseResponseOther
import com.driver.nyaku.ui.BaseActivity
import com.driver.nyaku.utils.invisible
import com.driver.nyaku.utils.visible
import com.google.gson.Gson
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_jadwal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLapanganFromJadwalActivity : BaseActivity(), AdapterJadwalForBooking.OnListener {
    val datajadwal: ArrayList<JadwalsItem> = ArrayList()
    var adapterLapangan: AdapterJadwalForBooking? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapangan_from_jadwal)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val product = intent.getParcelableExtra<LapangansItem>(LapangansItem::class.simpleName)

        tv_name.text = product?.namaLapangan
        tv_price.text = "Harga ${product?.harga} / Jam"
        tv_open.invisible()
        val glideUrl = GlideUrl(
            "${AppConstant.BASE_URL}show-image?image=${product?.gambar}",
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + Prefuser().getToken().toString())
                .build()
        )

        Glide.with(this)
            .load(glideUrl)
            .apply(RequestOptions.placeholderOf(R.drawable.no_image).error(R.drawable.no_image))

            .into(img_lapangan)

        adapterLapangan = this?.let {
            AdapterJadwalForBooking(
                it,
                product?.jadwals as MutableList<JadwalsItem>, this
            )
        }
        rv_jadwal.layoutManager = GridLayoutManager(this, 1)
        rv_jadwal.adapter = adapterLapangan
        adapterLapangan?.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickGrup(data: JadwalsItem) {
        startActivity(Intent(this,DetailJadwalActivity::class.java)
            .putExtra(JadwalsItem::class.java.name,data)
        )

//        datajadwal.add(data)
//        Log.e("Data", "long ${Gson().toJson(datajadwal)}")
//
//        btn_booking.visible()
//        btn_booking.setOnClickListener {
//            val api = ServiceGenerator.createService(
//                ApiInterface::class.java,
//                Prefuser().getToken(),
//                Constant.PASS
//            )
//            showLoading(this)
//            val data: ArrayList<Int> = ArrayList()
//            datajadwal.forEach {
//                it.id?.toInt()?.let { it1 -> data.add(it1) }
//            }
//            val body = BodyBooking(data, "baru")
//            Log.e("Data", "body ${Gson().toJson(body)}")
//            api.postBooking(body).enqueue(object : Callback<BaseResponseOther> {
//                override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
//
//                    hideLoading()
//                    if (response.isSuccessful) {
//                        if (response.body()?.status?.equals(true) == true)
//                            finish()
//                        showLongSuccessMessage("Sukses Booking")
//                    } else {
//                        showErrorMessage("Gagal booking")
//                    }
//                }
//
//                override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
//                    hideLoading()
//                    showErrorMessage("Periksa Internet")
//                }
//            })
//        }

    }
}