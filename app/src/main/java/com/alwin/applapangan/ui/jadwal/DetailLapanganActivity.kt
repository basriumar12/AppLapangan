package com.alwin.applapangan.ui.jadwal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.ResponseGedungItem
import com.alwin.applapangan.ui.home.AdapterGedung
import com.alwin.applapangan.ui.home.DetailShowGedungLapanganActivity
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponse
import com.driver.nyaku.ui.BaseActivity
import com.driver.nyaku.utils.visible
import com.google.gson.Gson
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLapanganActivity : BaseActivity() , AdapterGedung.OnListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapangan)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
       getData()
    }
    private fun getData() {
        val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Prefuser().getToken(),
            Constant.PASS
        )

        this?.let { showLoading(it) }
        api.getGedung(intent.getStringExtra("Data")).enqueue(object : Callback<BaseResponse<List<ResponseGedungItem>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<ResponseGedungItem>>>,
                response: Response<BaseResponse<List<ResponseGedungItem>>>
            ) {

                hideLoading()
                if (response.isSuccessful) {

                    val data = response.body()?.data
                    val adapterJadwal = AdapterGedung(
                        this@DetailLapanganActivity,
                        data as MutableList<ResponseGedungItem>,
                        this@DetailLapanganActivity
                    )
                    rv_lapangan.layoutManager = GridLayoutManager(this@DetailLapanganActivity, 2)
                    rv_lapangan.adapter = adapterJadwal
                    adapterJadwal?.notifyDataSetChanged()
                    if (data.isNullOrEmpty()) {
                        tv_empty.visible()
                    }
                    Log.e("TAG","data gedung ${Gson().toJson(data)}")

                }
            }

            override fun onFailure(call: Call<BaseResponse<List<ResponseGedungItem>>>, t: Throwable) {
                hideLoading()
                showErrorMessage("Periksa internet")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickGrup(data: ResponseGedungItem) {
        startActivity(
            Intent(this, DetailShowGedungLapanganFromJadwalActivity::class.java)
            .putExtra(ResponseGedungItem::class.simpleName, data)
        )
    }
}