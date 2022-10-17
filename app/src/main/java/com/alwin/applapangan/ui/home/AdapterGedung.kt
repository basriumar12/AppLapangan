package com.alwin.applapangan.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.ResponseGedungItem
import com.alwin.applapangan.utils.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.driver.nyaku.utils.invisible
import com.gorontalodigital.preference.Prefuser
import kotlinx.android.synthetic.main.activity_detail_lapangan_from_home.*
import kotlinx.android.synthetic.main.item_gedung.view.*

import java.util.*

class AdapterGedung(
    val context: Context, val data: MutableList<ResponseGedungItem>,
    private val itemListiner: OnListener
) :
    RecyclerView.Adapter<AdapterGedung.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<ResponseGedungItem>()

    init {
        dataFilterList = data as ArrayList<ResponseGedungItem>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_gedung, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClickGrup(data: ResponseGedungItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: ResponseGedungItem, listiner: OnListener) {

            itemView.setOnClickListener {
                listiner.onClickGrup(data)
            }


            itemView.tv_name.text = "Gedung "+data.namaGedung
                itemView.tv_price.text = "Alamat : ${data.alamat}"
            itemView.tv_date.text = "Kontak pemilik : ${data.kontakPemilik}"
            if (!data?.lapangans.isNullOrEmpty()) {
            val dataGambar = "${data?.lapangans?.get(0)?.gambar.toString()}"

                val glideUrl = GlideUrl(
                    "${AppConstant.BASE_URL}show-image?image=$dataGambar",
                    LazyHeaders.Builder()
                        .addHeader("Authorization", "Bearer " + Prefuser().getToken().toString())
                        .build()
                )

                Glide.with(itemView.context)
                    .load(glideUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image).error(R.drawable.no_image))

                    .into(itemView.img_lapangan)

            } else {
                Glide.with(itemView.context)
                    .load("")
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image).error(R.drawable.no_image))

                    .into(itemView.img_lapangan)
            }

        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<ResponseGedungItem>
                } else {
                    val resultList = ArrayList<ResponseGedungItem>()
                    for (row in data) {
                        if (row.namaGedung.toString()?.toLowerCase(Locale.ROOT)
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
                dataFilterList = results?.values as ArrayList<ResponseGedungItem>
                notifyDataSetChanged()
            }

        }
    }


}