package com.alwin.applapangan.ui.jadwal.detail_lapangan

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.BodyBookingNew
import com.alwin.applapangan.models.gedung.JadwalsItem
import com.alwin.applapangan.models.gedung.LapangansItem
import com.alwin.applapangan.models.jadwal.BodyCekJadwal
import com.alwin.applapangan.service.NotificationManager
import com.alwin.applapangan.ui.jadwal.detail.AdapterDetailJadwalForBooking
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_detail_jadwal.*
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_jadwal.*
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_jadwal.btn_booking
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_jadwal.btn_cek
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_jadwal.rv_jadwal
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_jadwal.spn_mulai
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_jadwal.spn_selesai
import kotlinx.android.synthetic.main.item_jadwal_for_booking.view.*
import kotlinx.android.synthetic.main.view_group.view.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DetailLapanganFromJadwalActivity : BaseActivity(), AdapterJadwalForBooking.OnListener,
    AdapterJadwalForBooking2.OnListener,
    AdapterDetailJadwalForBooking.OnListener {
    val datajadwal: ArrayList<JadwalsItem> = ArrayList()
    var adapterLapangan: AdapterJadwalForBooking? = null
    lateinit var dialog: BottomSheetDialog
    var spinnerMulai: Spinner? = null
    var spinnerSelesai: Spinner? = null
    var jamMulai = 6
    var jamSelesai = 23
    var data: JadwalsItem? = null
    var day = ""
    var dayKode = ""
    var dayKodeFromChooseJadwal = ""
    var dayId = ""
    var product: LapangansItem? = null
    var cekJam = false
    var cekTanggal = false
    val sdfCureentDateJam = SimpleDateFormat("HH:mm:ss")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapangan_from_jadwal)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        product = intent.getParcelableExtra<LapangansItem>(LapangansItem::class.simpleName)
        dialog = BottomSheetDialog(this)
        tv_name.text = product?.namaLapangan
        tv_jenis_lapangan.text = "Jenis Lapangan : ${product?.jenisLapangan}"
        tv_wc.text = "WC : ${product?.wc}"
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

        pilihTanggalDanCekJadwal()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun pilihTanggalDanCekJadwal() {
        tv_tanggal.setOnClickListener {
            setCalendar()
        }

        tv_jadwal.setOnClickListener {
            showListJadwal()
        }
        val dataAdapter: MutableList<String> = mutableListOf<String>()

        for (data in 6..23) {
            dataAdapter.add(data.toString())
        }

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

        spinnerMulai?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                jamMulai = dataAdapter[position]?.toInt()
                btn_booking.invisible()
                val jamNow = sdfCureentDateJam.format(Date()).toString().substring(0, 2)
                if (cekTanggal == true && jamMulai < jamNow.toInt()) {
                    showErrorMessage("Jam yang dipilih pada hari ini sudah lewat")
                    cekJam = true
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        spinnerSelesai?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                jamSelesai = dataAdapter[position]?.toInt()
                btn_booking.invisible()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        btn_cek.setOnClickListener {
            val jamNow = sdfCureentDateJam.format(Date()).toString().substring(0, 2)
            if (jamSelesai <= jamMulai) {
                showErrorMessage("Jam Selesai tidak bisa lebih kecil dari jam mulai")
            } else if (tv_tanggal.text.toString().isNullOrEmpty()) {
                showErrorMessage("Tanggal belum dipilih")
            } else if (tv_jadwal.text.toString().isNullOrEmpty()) {
                showErrorMessage("Tanggal belum dipilih")
            } else if (cekJam == true) {
                showErrorMessage("Jam yang dipilih pada hari ini sudah lewat")
            }
            else if (cekTanggal == true && jamMulai < jamNow.toInt()) {
                showErrorMessage("Jam yang dipilih pada hari ini sudah lewat")

            }
            else
                cekJadwal(jamMulai, jamSelesai)
        }

        btn_booking.setOnClickListener {
            bookingJadwal(jamMulai, jamSelesai)
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
        val id = dayId

        dataId.add(id.toString().toInt())
        val body = BodyBookingNew(
            jamSelesai.toString() + ":00",
            jamMulai.toString() + ":00",
            dataId,
            "baru",
            tv_tanggal.text.toString()
        )
        Log.e("TAG", "${Gson().toJson(body)}")
        api.postBookingNew(body).enqueue(object : Callback<BaseResponseOther> {
            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {

                hideLoading()
                if (response.isSuccessful) {
                    if (response.body()?.status?.equals(true) == true) {
                        finish()
                        showLongSuccessMessage("Sukses Booking")
                        NotificationManager(this@DetailLapanganFromJadwalActivity).displayNotification(
                            "Berhasil Booking",
                            "Selamat anda berhasil booking jadwal",
                            "",
                            ""
                        )
                    } else {
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


        val body = BodyCekJadwal(
            jamSelesai.toString() + ":00",
            dayId,
            jamMulai.toString() + ":00",
            tv_tanggal.text.toString()
        )
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

    private fun kodeDayConvert(): String {

        when (day) {
            "Monday" -> {
                dayKode = "01"
            }
            "Tuesday" -> {
                dayKode = "02"
            }
            "Wednesday" -> {
                dayKode = "03"
            }
            "Thursday" -> {
                dayKode = "04"
            }
            "Friday" -> {
                dayKode = "05"
            }
            "Saturday" -> {
                dayKode = "06"
            }
            "Sunday" -> {
                dayKode = "07"
            }
            "Senin" -> {
                dayKode = "01"
            }
            "Selasa" -> {
                dayKode = "02"
            }
            "Rabu" -> {
                dayKode = "03"
            }
            "Kamis" -> {
                dayKode = "04"
            }
            "Jumat" -> {
                dayKode = "05"
            }
            "Sabtu" -> {
                dayKode = "06"
            }
            "Minggu" -> {
                dayKode = "07"
            }
        }
        return dayKode
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setCalendar() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)
        val simpleDateFormat = SimpleDateFormat("EEEE")

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            val month = monthOfYear.toInt() + 1
            // Display Selected date in textbox
            val date = Date(year, monthOfYear, dayOfMonth - 1)
            val dayString = simpleDateFormat.format(date) //
            this.day = dayString
            //cek tanggal
            val sdfCureentDate = LocalDate.now()
            val dateChoose = "$year-$month-$dayOfMonth"
            if (dateChoose.toString().equals("${sdfCureentDate.year}-${sdfCureentDate.monthValue}-${sdfCureentDate.dayOfMonth}")) {
                cekTanggal = true
            } else
                cekTanggal = false

            System.out.println(" C DATE is $cekTanggal  $dateChoose ${sdfCureentDate.year}-${sdfCureentDate.monthValue}-${sdfCureentDate.dayOfMonth}")

            if (kodeDayConvert() != dayKodeFromChooseJadwal) {
                showLongErrorMessage("Jadwal yang di pilih berbeda dengan tanggal yang dipilih")
                tv_tanggal.setText("")
                tv_tanggal.setHint("Pilih Tanggal")
            } else if (validateDate(year,month,dayOfMonth)) {
                showLongErrorMessage("Jadwal yang di pilih sudah lewat hari")
                tv_tanggal.setText("")
                tv_tanggal.setHint("Pilih Tanggal")
            } else {
                tv_tanggal.setText("$year-$month-$dayOfMonth")
            }

        }, year, month, day)

        dpd.show()
    }

    fun validateDate(year: Int, month: Int, day: Int): Boolean {
        val dateToValidate = Calendar.getInstance()
        val currentDate = Calendar.getInstance()
        dateToValidate.set(year, month, day)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDate.set(
                LocalDate.now().year,
                LocalDate.now().monthValue,
                LocalDate.now().dayOfMonth
            )
        }
        val dateInMilliseconds = dateToValidate.timeInMillis
        val currentTimeInMilliseconds = currentDate.timeInMillis
        return dateInMilliseconds < currentTimeInMilliseconds
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun showListJadwal() {

        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.view_group, null)
        val window = dialog.window
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(view)

        view.title_dialog.setText("Daftar Jadwal")

        val adapterLapangan = this?.let {
            AdapterJadwalForBooking2(
                it,
                product?.jadwals as MutableList<JadwalsItem>, this
            )
        }

        view.rv_jadwal.adapter = adapterLapangan
        view.rv_jadwal.layoutManager = GridLayoutManager(this, 1)
        adapterLapangan?.notifyDataSetChanged()
        view.img_close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    fun showJadwal(data: JadwalsItem) {
        this.data = data
        dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.view_group, null)
        val window = dialog.window
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setContentView(view)

        view.title_dialog.setText("Daftar Jam tersedia")

        val dataMulai = "${data?.mulai?.substring(0, 2)}"
        val dataSelesai = "${data?.selesai?.substring(0, 2)}"
        val dataAdapter: MutableList<String> = mutableListOf<String>()
        for (data in dataMulai.toInt()..dataSelesai.toInt()) {
            dataAdapter.add(data.toString())
        }

        val adapterDetailJadwalForBooking = AdapterDetailJadwalForBooking(this, dataAdapter, this)
        view.rv_jadwal.adapter = adapterDetailJadwalForBooking
        view.rv_jadwal.layoutManager = GridLayoutManager(this, 2)
        adapterDetailJadwalForBooking?.notifyDataSetChanged()
        view.img_close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onClickGrup(data: JadwalsItem) {
        showJadwal(data)
//        startActivity(Intent(this,DetailJadwalActivity::class.java)
//            .putExtra(JadwalsItem::class.java.name,data)
//        )

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

    override fun onClickGrup(data: String) {

    }

    override fun onClickGrupdata(data: JadwalsItem) {
        dayId = data.id.toString()
        dayKodeFromChooseJadwal = data.kodeHari.toString()
        dialog.dismiss()
        when (data.kodeHari?.toInt()) {
            1 -> {
                tv_jadwal.text = "Hari Senin"
            }
            2 -> {
                tv_jadwal.text = "Hari Selasa"
            }
            3 -> {
                tv_jadwal.text = "Hari Rabu"
            }
            4 -> {
                tv_jadwal.text = "Hari Kamis"
            }
            5 -> {
                tv_jadwal.text = "Hari Jumat"
            }
            6 -> {
                tv_jadwal.text = "Hari Sabtu"
            }
            7 -> {
                tv_jadwal.text = "Hari Minggu"
            }
        }

    }
}