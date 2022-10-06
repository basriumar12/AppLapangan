package com.alwin.applapangan.ui.gedung

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.ResponseGedungItem
import com.driver.nyaku.utils.invisible
import kotlinx.android.synthetic.main.item_jadwal.view.*
import kotlinx.android.synthetic.main.item_jadwal.view.img_lapangan
import kotlinx.android.synthetic.main.item_lapangan.view.*
import kotlinx.android.synthetic.main.item_lapangan.view.tv_name
import kotlinx.android.synthetic.main.item_lapangan.view.tv_price
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal, parent, false)

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


            itemView.tv_name.text = data.namaGedung
                itemView.tv_price.text = "Alamat : ${data.alamat}"
            itemView.tv_date.text = "Kontak pemilik : ${data.kontakPemilik}"

            itemView.img_lapangan.invisible()


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