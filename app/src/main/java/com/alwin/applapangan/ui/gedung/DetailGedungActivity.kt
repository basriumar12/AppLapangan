package com.alwin.applapangan.ui.gedung

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.BodyBooking
import com.alwin.applapangan.models.booking.ResponseBooking
import com.alwin.applapangan.models.jadwal.ResponseJadwal
import com.alwin.applapangan.models.lapangan.ResponseLapangan
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
import com.driver.nyaku.models.BaseResponseOther
import com.driver.nyaku.ui.BaseActivity
import com.driver.nyaku.utils.currencyFormatter
import com.driver.nyaku.utils.invisible
import com.google.gson.Gson
import com.gorontalodigital.preference.Prefuser
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_detail_lapangan.*
import org.json.JSONObject
import pl.aprilapps.easyphotopicker.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DetailGedungActivity : BaseActivity() {
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
        tv_name.text = "Nama Lapangan : " + product?.jadwal?.lapangan?.namaLapangan
        tv_price.text = "Harga perjam : " + currencyFormatter(product?.jadwal?.lapangan?.harga.toString())
        tv_date.invisible()

        tv_gedung.text =
            "Lokasi Lapangan berada pada gedung : ${product?.jadwal?.lapangan?.gedung?.namaGedung} - Dapat menghubungi pemilik :  ${product?.jadwal?.lapangan?.gedung?.kontakPemilik}"
        tv_deskripsi.text =
            "Alamat Lapangan : ${product?.jadwal?.lapangan?.gedung?.alamat} - ${product?.jadwal?.lapangan?.gedung?.provinsi}, ${product?.jadwal?.lapangan?.gedung?.kabupaten}, ${product?.jadwal?.lapangan?.gedung?.kecamatan}, Desa ${product?.jadwal?.lapangan?.gedung?.desa}"

        val glideUrl = GlideUrl(
            "${AppConstant.BASE_URL}show-image?image=${product?.jadwal?.lapangan?.gambar}",
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + Prefuser().getToken().toString())
                .build()
        )

        Glide.with(this)
            .load(glideUrl)
            .into(img_lapangan)
        btn_booking.invisible()
        btn_booking.setOnClickListener {
            if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
                easyImage.openCameraForImage(this)


            } else {
                EasyPermissions.requestPermissions(
                    this, "This application need your permission to access photo gallery.",
                    91, android.Manifest.permission.CAMERA
                )
            }
        }


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
                    var file = File(imageFiles.get(0).file.toString())
                    imageFiles.let {
                        myFile = File(it.get(0).file.toString())
                        uploadPhoto()


                    }


                }
            })
    }

    private fun uploadPhoto() {
        showLoading(this)
        AndroidNetworking.upload("${AppConstant.BASE_URL}pembayaran")
            .addMultipartFile("bukti_bayar", myFile)
            .addMultipartParameter("booking_id",idBooking)
            .addHeaders("Authorization", "Bearer ${Prefuser().getToken()}")
            .setTag("uploadTest")
            .setPriority(Priority.HIGH)
            .build()
            .setUploadProgressListener(object : UploadProgressListener {
                override fun onProgress(bytesUploaded: Long, totalBytes: Long) {
                    showLoading(this@DetailGedungActivity)
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
                    val data = jsonObject.getString("data").toString()
                    val file = JSONObject(data.toString()).getString("file")
                    val datafileImage = JSONObject(file.toString()).getString("file_url")


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
}