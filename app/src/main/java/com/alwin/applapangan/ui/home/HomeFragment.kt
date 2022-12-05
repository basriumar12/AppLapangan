package com.alwin.applapangan.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.ResponseGedungItem
import com.alwin.applapangan.ui.jadwal.DetailShowGedungLapanganFromJadwalActivity
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponse
import com.driver.nyaku.ui.BaseFragment
import com.driver.nyaku.utils.visible
import com.google.gson.Gson
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.fragment_home.tv_empty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment(), AdapterGedung.OnListener {

    lateinit var rv_lapangan: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rv_lapangan = view.findViewById(R.id.rv_lapangan)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()

    }

    private fun getData() {
        val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Prefuser().getToken(),
            Constant.PASS
        )

        activity?.let { showLoading(it) }
        api.getGedung().enqueue(object : Callback<BaseResponse<List<ResponseGedungItem>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<ResponseGedungItem>>>,
                response: Response<BaseResponse<List<ResponseGedungItem>>>
            ) {

                hideLoading()
                if (response.isSuccessful) {

                    val data = response.body()?.data
                    val adapterJadwal = activity?.let {
                        AdapterGedung(
                            it,
                            data as MutableList<ResponseGedungItem>,
                            this@HomeFragment
                        )
                    }
                    rv_lapangan.layoutManager = GridLayoutManager(activity, 2)
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


//    private fun getData() {
//        val api = ServiceGenerator.createService(
//            ApiInterface::class.java,
//            Prefuser().getToken(),
//            Constant.PASS
//        )
//
//        activity?.let { showLoading(it) }
//        api.lapangan.enqueue(object : Callback<BaseResponse<List<ResponseLapangan>>> {
//            override fun onResponse(
//                call: Call<BaseResponse<List<ResponseLapangan>>>,
//                response: Response<BaseResponse<List<ResponseLapangan>>>
//            ) {
//                hideLoading()
//                if (response.isSuccessful) {
//
//                    if (response.body()?.status == true) {
//
//                        val data = response.body()?.data
//                        val adapterLapangan = activity?.let {
//                            AdapterLapangan(
//                                it,
//                                data as MutableList<ResponseLapangan>, this@HomeFragment)}
//                        rv_lapangan.layoutManager = GridLayoutManager(activity,2)
//                        rv_lapangan.adapter = adapterLapangan
//                        adapterLapangan?.notifyDataSetChanged()
//
//                        if (data.isNullOrEmpty()){
//                            tv_empty.visible()
//                        }
//                    } else {
//                        val jObjError = JSONObject(response.errorBody()!!.string())
//                        showErrorMessage("Gagal dapatakan data,${jObjError.getString("message")}")
//
//                    }
//                } else {
//                    val jObjError = JSONObject(response.errorBody()!!.string())
//                    showErrorMessage("Gagal dapatakan data,${jObjError.getString("message")}")
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<BaseResponse<List<ResponseLapangan>>>, t: Throwable) {
//
//                hideLoading()
//                showErrorMessage("Gagal dapatkan data, periksa jaringan internet")
//            }
//        })
//
//    }


    override fun onClickGrup(data: ResponseGedungItem) {
        startActivity(Intent(activity, DetailShowGedungLapanganFromJadwalActivity::class.java)
            .putExtra(ResponseGedungItem::class.simpleName, data)
        )
    }


}