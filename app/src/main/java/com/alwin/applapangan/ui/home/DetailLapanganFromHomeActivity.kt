package com.alwin.applapangan.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.LapangansItem
import com.alwin.applapangan.models.gedung.ResponseGedungItem
import com.alwin.applapangan.utils.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.driver.nyaku.ui.BaseActivity
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_detail_lapangan.*
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_home.*
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_home.img_lapangan
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_home.tv_name
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_home.tv_price

class DetailLapanganFromHomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lapangan_from_home)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        val product = intent.getParcelableExtra<LapangansItem>(LapangansItem::class.simpleName)

        tv_name.text = product?.namaLapangan
        tv_price.text = "Harga ${product?.harga} / Jam"
        tv_open.text = "Waktu Buka  ${product?.waktuBuka} - Waktu Tutup ${product?.waktuTutup}"
        val glideUrl = GlideUrl(
            "${AppConstant.BASE_URL}show-image?image=${product?.gambar}",
            LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer "+ Prefuser().getToken().toString())
                .build()
        )

        Glide.with(this)
            .load(glideUrl)
            .apply(RequestOptions.placeholderOf(R.drawable.no_image).error(R.drawable.no_image))

            .into(img_lapangan)
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