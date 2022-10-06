package com.alwin.applapangan.ui.transaksi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.transaksi.ResponseTransaksi
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.driver.nyaku.models.BaseResponse
import com.driver.nyaku.ui.BaseFragment
import com.driver.nyaku.utils.visible
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.fragment_slideshow.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransaksiFragment : BaseFragment(), AdapterTransaksi.OnListener {



    lateinit var rv_transaksi: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_slideshow, container, false)
        rv_transaksi = view.findViewById(R.id.rv_transaksi)
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
        api.getTransaksi().enqueue(object : Callback<BaseResponse<List<ResponseTransaksi>>>{
            override fun onResponse(
                call: Call<BaseResponse<List<ResponseTransaksi>>>,
                response: Response<BaseResponse<List<ResponseTransaksi>>>
            ) {
                hideLoading()
                if (response.isSuccessful) {

                    if (response.body()?.status == true) {
                        val data = response.body()?.data
                        val adapterTransaksi = activity?.let { AdapterTransaksi(it,
                            data as MutableList<ResponseTransaksi>, this@TransaksiFragment) }
                        rv_transaksi.layoutManager = LinearLayoutManager(activity)
                        rv_transaksi.adapter = adapterTransaksi
                        adapterTransaksi?.notifyDataSetChanged()

                        if (data.isNullOrEmpty()){
                            tv_empty.visible()
                        }
                    }else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        showErrorMessage("Gagal dapatakan data ,${jObjError.getString("message")}")

                    }

                } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    showErrorMessage("Gagal dapatakan data ,${jObjError.getString("message")}")

                }
            }

            override fun onFailure(call: Call<BaseResponse<List<ResponseTransaksi>>>, t: Throwable) {
                
                hideLoading()
                showErrorMessage("Gagal dapatkan data, periksa jaringan internet")
            }
        })
    }

    override fun onClickGrup(data: ResponseTransaksi) {
        
    }


}