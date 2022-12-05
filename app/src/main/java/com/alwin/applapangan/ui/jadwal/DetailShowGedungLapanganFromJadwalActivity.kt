package com.alwin.applapangan.ui.jadwal

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.LapangansItem
import com.alwin.applapangan.models.gedung.ResponseGedungItem
import com.alwin.applapangan.ui.home.AdapterLapanganDetail
import com.alwin.applapangan.ui.jadwal.detail_lapangan.DetailLapanganFromJadwalActivity
import com.alwin.applapangan.utils.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.driver.nyaku.ui.BaseActivity
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_detail_lapangan_show.*
import kotlinx.android.synthetic.main.activity_detail_lapangan_show.img_lapangan
import kotlinx.android.synthetic.main.activity_detail_lapangan_show.tv_name

class DetailShowGedungLapanganFromJadwalActivity : BaseActivity(), AdapterLapanganDetail.OnListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapangan_show)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val product = intent.getParcelableExtra<ResponseGedungItem>(ResponseGedungItem::class.simpleName)
        tv_name.text = "Nama Gedung : " + product?.namaGedung
        tv_kontak.text = "Kontak Pemilik : " + product?.kontakPemilik
        tv_alamat.text =
            "Kontak Pemilik : ${product?.alamat} - ${product?.provinsi}, ${product?.kabupaten} - ${product?.desa} "

        val glideUrl = GlideUrl(
            "${AppConstant.BASE_URL}show-image?image=${product?.lapangans?.get(0)?.gambar}",
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer "+ Prefuser().getToken().toString())
                .build()
        )

        Glide.with(this)
            .load(glideUrl)
            .apply(RequestOptions.placeholderOf(R.drawable.no_image).error(R.drawable.no_image))

            .into(img_lapangan)


        val adapterLapangan = this?.let {
            AdapterLapanganDetail(
                it,
                product?.lapangans as MutableList<LapangansItem>, this
            )
        }
        rv_lapangan.layoutManager = GridLayoutManager(this, 2)
        rv_lapangan.adapter = adapterLapangan
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

    override fun onClickGrup(data: LapangansItem) {
        startActivity(
            Intent(this, DetailLapanganFromJadwalActivity::class.java)
            .putExtra(LapangansItem::class.simpleName, data)
        )
    }
}