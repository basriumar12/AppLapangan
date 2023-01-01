package com.alwin.applapangan.ui.booking

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.BookingDetailsItem
import com.alwin.applapangan.models.booking.ResponseBooking
import com.alwin.applapangan.utils.ApiInterface
import com.alwin.applapangan.utils.AppConstant
import com.alwin.applapangan.utils.Constant
import com.alwin.applapangan.utils.ServiceGenerator
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.UploadProgressListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.driver.nyaku.models.BaseResponseOther
import com.driver.nyaku.ui.BaseActivity
import com.driver.nyaku.utils.currencyFormatter
import com.driver.nyaku.utils.invisible
import com.driver.nyaku.utils.visible
import com.gorontalodigital.preference.Prefuser
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_detail_lapangan_booking.*
import kotlinx.android.synthetic.main.activity_detail_lapangan_booking.btn_booking
import kotlinx.android.synthetic.main.activity_detail_lapangan_booking.img_lapangan
import kotlinx.android.synthetic.main.activity_detail_lapangan_booking.rv_jadwal
import kotlinx.android.synthetic.main.activity_detail_lapangan_booking.tv_name
import org.json.JSONObject
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DetailShowLapanganBookingActivity : BaseActivity(), AdapterJadwalBooking.OnListener {
    var myFile: File? = null
    var idBooking = ""
    lateinit var easyImage: EasyImage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapangan_booking)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        permissionInit()
        easyImage = EasyImage.Builder(this).setCopyImagesToPublicGalleryFolder(false)
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .setFolderName("AppLapangan")
            .allowMultiple(true)
            .build()
        val product = intent.getParcelableExtra<ResponseBooking>(ResponseBooking::class.simpleName)
        idBooking = product?.id.toString()
        tv_name.text = "Nama Lapangan : " + product?.bookingDetails?.get(0)?.jadwal?.lapangan?.namaLapangan
        tv_status.text = "Status : ${product?.status}"
        tv_price_total.text = "Total Harga : " + product?.totalBayar?.toString()?.let { currencyFormatter(it) }

        tv_gedung.text =
            "Lokasi Lapangan berada pada gedung : ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.namaGedung} - Dapat menghubungi pemilik :  ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.kontakPemilik}"
        tv_deskripsi.text =
            "Alamat Lapangan : ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.alamat} - ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.provinsi}, ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.kabupaten}, ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.kecamatan}, Desa ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.desa}"
        tv_rekening.text = "Catatan : Pembayaran melalui Nama BANK : ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.rekening?.namaBank} No rekening : ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.rekening?.noRekening} Atas Nama : ${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gedung?.rekening?.namaPemilikAkun}"
        val glideUrl = GlideUrl(
            "${AppConstant.BASE_URL}show-image?image=${product?.bookingDetails?.get(0)?.jadwal?.lapangan?.gambar}",
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + Prefuser().getToken().toString())
                .build()
        )


        val adapterLapangan = this?.let {
            AdapterJadwalBooking(
                it,
                product?.bookingDetails as MutableList<BookingDetailsItem>, this
            )
        }
        rv_jadwal.layoutManager = GridLayoutManager(this, 1)
        rv_jadwal.adapter = adapterLapangan
        adapterLapangan?.notifyDataSetChanged()

        Glide.with(this)
            .load(glideUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.no_image).error(R.drawable.no_image))

            .into(img_lapangan)


        btn_booking.setOnClickListener {
            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                easyImage.openGallery(this)


            } else {
                EasyPermissions.requestPermissions(
                    this, "This application need your permission to access photo gallery.",
                    91, android.Manifest.permission.CAMERA
                )
            }
        }

        btn_cancel.setOnClickListener {
            cancelBooking()
        }
        if (product?.status.equals("pembayaran terverifikasi")){
            btn_booking.invisible()
        }
        else if (product?.status.equals("batal")){
            btn_booking.invisible()
            tv_status_booking.visible()

        }

    }

    private fun cancelBooking() {
        val api = ServiceGenerator.createService(
            ApiInterface::class.java,
            Prefuser().getToken(),
            Constant.PASS
        )
       showLoading(this)
        api.cancelBooking(idBooking).enqueue(object : Callback<BaseResponseOther>{
            override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
                hideLoading()
                if (response.isSuccessful){
                        showLongSuccessMessage("Berhasil Cancel Booking")
                        finish()
                    } else {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    showErrorMessage("Gagal,${jObjError.getString("message")}")
                    }

            }

            override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
               hideLoading()
                showErrorMessage("Gagal, Periksa Jaringan")
            }
        })
    }

    private fun permissionInit() {
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (it.areAllPermissionsGranted()) {
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(requestCode, resultCode, data, this,
            object : DefaultCallback() {
                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    val file = File(imageFiles.get(0).file.toString())
                   // Glide.with(this@DetailShowLapanganBookingActivity).load(file).into(img_bukti)
                    myFile = File(imageFiles.get(0).file.toString())
                    imageFiles.let {
                        //myFile = File(it.get(0).file.toString())
                    uploadPhoto()
                        Glide.with(this@DetailShowLapanganBookingActivity).load(myFile).into(img_bukti)


                    }


                }
            })
    }

    private fun uploadPhoto() {
//
//        var multipartImage: MultipartBody.Part? = null
//         val requestFile = RequestBody.create(MediaType.parse("image/*"), myFile?.path)
//
//        val body = MultipartBody.Part.createFormData("bukti_bayar", myFile?.name, requestFile)
//
//
//        val bookingId: RequestBody = RequestBody.create(
//            MediaType.parse("text/plain"),
//            idBooking
//
//        )
//
//        val api = ServiceGenerator.createService(
//            ApiInterface::class.java,
//            Prefuser().getToken(),
//            Constant.PASS
//        )
//        showLoading(this)
//        Log.e("TAG","data body $bookingId ${body.body()}")
//        api.uploadFIle(bookingId,body)
//            .enqueue(object : Callback<BaseResponseOther>{
//                override fun onResponse(call: Call<BaseResponseOther>, response: Response<BaseResponseOther>) {
//                        hideLoading()
//                    if (response.isSuccessful){
//                        showLongSuccessMessage("Berhasil Upload")
//                        finish()
//                    } else {
//                        showInfoMessage("Gagal Upload")
//                    }
//                    Log.e("TAG"," ${response.code()} ${response.isSuccessful} ${response.body()?.message}")
//                }
//
//                override fun onFailure(call: Call<BaseResponseOther>, t: Throwable) {
//                    hideLoading()
//                    showErrorMessage("gagal upload")
//                    Log.e("TAG","gagal upload ${t.message}")
//                }
//            })
//
//        Log.e("TAG","$idBooking ${myFile?.name}")
       uploadFastNet()
    }

    private fun uploadFastNet() {
        showLoading(this)
        val file = myFile?.path
        AndroidNetworking.upload("${AppConstant.BASE_URL}pembayaran")
            .addMultipartFile("bukti_bayar", myFile)
            .addMultipartParameter("booking_id",idBooking)
            .addHeaders("Authorization", "Bearer ${Prefuser().getToken()}")
            .setTag("uploadTest")
            .setPriority(Priority.HIGH)
            .build()
            .setUploadProgressListener(object : UploadProgressListener {
                override fun onProgress(bytesUploaded: Long, totalBytes: Long) {
                    showLoading(this@DetailShowLapanganBookingActivity)
                }
            })
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onError(anError: ANError?) {
                    hideLoading()
                    Log.e("TAG", "error ${anError.toString()}")
                    showLongErrorMessage("Gagal upload foto")
                }

                override fun onResponse(response: JSONObject?) {
                    hideLoading()
                    val jsonObject = JSONObject(response.toString())

                    val msg = jsonObject.getString("message").toString()
                    //  val data = jsonObject.getString("data").toString()
//                    val file = JSONObject(data.toString()).getString("file")
//                    val datafileImage = JSONObject(file.toString()).getString("file_url")


                    if (msg.equals("Pembayaran created")) {
                        showLongSuccessMessage("Sukses upload")
                        finish()
                    } else {
                        showErrorMessage("Gagal upload")
                    }


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

    override fun onClickGrup(data: BookingDetailsItem) {

    }
}