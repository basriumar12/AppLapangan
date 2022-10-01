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
import com.driver.nyaku.utils.currencyFormatter
import kotlinx.android.synthetic.main.item_jadwal.view.*
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
            itemView.tv_price.text = "Harga : Rp" + currencyFormatter(data.lapangan?.harga.toString())
            itemView.tv_date.text = "Jadwal : ${data.tanggal} / jam : ${data?.mulai} - ${data.selesai} "
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