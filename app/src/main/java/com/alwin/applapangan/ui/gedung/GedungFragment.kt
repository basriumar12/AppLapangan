package com.alwin.applapangan.ui.gedung

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.ResponseGedungItem
import com.alwin.applapangan.models.jadwal.ResponseJadwal
import com.alwin.applapangan.models.lapangan.ResponseLapangan
import com.alwin.applapangan.ui.jadwal.DetailLapanganActivity
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponse
import com.driver.nyaku.ui.BaseFragment
import com.driver.nyaku.utils.visible
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GedungFragment : BaseFragment(), AdapterGedung.OnListener {

    lateinit var rv_jadwal: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        rv_jadwal = view.findViewById(R.id.rv_jadwal)
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
        api.gedung.enqueue(object : Callback<BaseResponse<List<ResponseGedungItem>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<ResponseGedungItem>>>,
                response: Response<BaseResponse<List<ResponseGedungItem>>>
            ) {

                hideLoading()
                if (response.isSuccessful) {

                    if (response.body()?.status == true) {
                        val data = response.body()?.data
                        val adapterJadwal = activity?.let { AdapterGedung(it, data as MutableList<ResponseGedungItem>,this@GedungFragment) }
                        rv_jadwal.layoutManager = GridLayoutManager(activity,2)
                        rv_jadwal.adapter = adapterJadwal
                        adapterJadwal?.notifyDataSetChanged()
                        if (data.isNullOrEmpty()){
                            tv_empty.visible()
                        }
                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        showErrorMessage("Gagal dapatakan data ,${jObjError.getString("message")}")

                    }
                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    showErrorMessage("Gagal dapatakan data ,${jObjError.getString("message")}")

                }
            }

            override fun onFailure(call: Call<BaseResponse<List<ResponseGedungItem>>>, t: Throwable) {

            }
        })
    }

    override fun onClickGrup(data: ResponseGedungItem) {
        startActivity(Intent(activity, DetailLapanganActivity::class.java)
            .putExtra(ResponseGedungItem::class.simpleName, data)
        )
    }


}