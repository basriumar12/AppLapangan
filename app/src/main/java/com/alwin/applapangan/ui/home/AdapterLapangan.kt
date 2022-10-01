package com.alwin.applapangan.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.lapangan.ResponseLapangan
import com.bumptech.glide.Glide
import com.driver.nyaku.utils.currencyFormatter
import kotlinx.android.synthetic.main.item_lapangan.view.*
import java.util.*

class AdapterLapangan(
    val context: Context, val data: MutableList<ResponseLapangan>,
    private val itemListiner: OnListener
) :
    RecyclerView.Adapter<AdapterLapangan.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<ResponseLapangan>()

    init {
        dataFilterList = data as ArrayList<ResponseLapangan>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lapangan, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClickGrup(data: ResponseLapangan)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: ResponseLapangan, listiner: OnListener) {

            itemView.setOnClickListener {
                listiner.onClickGrup(data)
            }


            itemView.tv_name.text = "Nama : " + data.namaLapangan
            itemView.tv_price.text = "Harga : Rp" + currencyFormatter(data.harga.toString())
           // Glide.with(itemView.context).load(data.harga).into(itemView.img_lapangan)



        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<ResponseLapangan>
                } else {
                    val resultList = ArrayList<ResponseLapangan>()
                    for (row in data) {
                        if (row.namaLapangan?.toString()?.toLowerCase(Locale.ROOT)
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
                dataFilterList = results?.values as ArrayList<ResponseLapangan>
                notifyDataSetChanged()
            }

        }
    }


}