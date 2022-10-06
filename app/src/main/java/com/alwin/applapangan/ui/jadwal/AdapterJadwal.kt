package com.alwin.applapangan.ui.jadwal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.jadwal.ResponseJadwal
import com.alwin.applapangan.utils.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.driver.nyaku.utils.currencyFormatter
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.item_jadwal.view.*
import kotlinx.android.synthetic.main.item_jadwal.view.img_lapangan
import kotlinx.android.synthetic.main.item_lapangan.view.*
import kotlinx.android.synthetic.main.item_lapangan.view.tv_name
import kotlinx.android.synthetic.main.item_lapangan.view.tv_price
import java.util.*

class AdapterJadwal(
    val context: Context, val data: MutableList<ResponseJadwal>,
    private val itemListiner: OnListener
) :
    RecyclerView.Adapter<AdapterJadwal.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<ResponseJadwal>()

    init {
        dataFilterList = data as ArrayList<ResponseJadwal>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClickGrup(data: ResponseJadwal)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: ResponseJadwal, listiner: OnListener) {

            itemView.setOnClickListener {
                listiner.onClickGrup(data)
            }


            itemView.tv_name.text = data.lapangan?.namaLapangan
            try {

                itemView.tv_price.text = "Harga : Rp" + currencyFormatter(data.lapangan?.harga.toString())
            } catch (e: NumberFormatException) {
                itemView.tv_price.text = "Harga : Rp" + data.lapangan?.harga.toString()

            }
            itemView.tv_date.text = "Jadwal : ${data.tanggal} / jam : ${data?.mulai} - ${data.selesai} "

            val glideUrl = GlideUrl(
                "${AppConstant.BASE_URL}show-image?image=${data.lapangan?.gambar}",
                LazyHeaders.Builder()
                    .addHeader("Authorization", "Bearer " + Prefuser().getToken().toString())
                    .build()
            )
            Glide.with(itemView.context)
                .load(glideUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.no_image).error(R.drawable.no_image))
                .into(itemView.img_lapangan)

            // Glide.with(itemView.context).load(data.harga).into(itemView.img_lapangan)


        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<ResponseJadwal>
                } else {
                    val resultList = ArrayList<ResponseJadwal>()
                    for (row in data) {
                        if (row.lapangan?.namaLapangan?.toString()?.toLowerCase(Locale.ROOT)
                                ?.contains(charSearch.toLowerCase(Locale.ROOT))!!
                        ) {
                            resultList.add(row)
                        }
                    }
                    dataFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilterList = results?.values as ArrayList<ResponseJadwal>
                notifyDataSetChanged()
            }

        }
    }


}