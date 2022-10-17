package com.alwin.applapangan.ui.jadwal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.bumptech.glide.Glide
import com.driver.nyaku.utils.invisible
import kotlinx.android.synthetic.main.item_jadwal.view.*
import java.util.*

class AdapterKabKota(
    val context: Context, val data: MutableList<String>,
    private val itemListiner: OnListener
) :
    RecyclerView.Adapter<AdapterKabKota.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<String>()

    init {
        dataFilterList = data as ArrayList<String>
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
        fun onClickGrup(data: String)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: String, listiner: OnListener) {

            itemView.setOnClickListener {
                listiner.onClickGrup(data)
            }


            itemView.tv_name.text = data
            itemView.tv_date.invisible()
            itemView.tv_price.invisible()
            Glide.with(itemView.context).load(R.drawable.ic_baseline_bungalow_24).into(itemView.img_lapangan)
            ///itemView.img_lapangan.invisible()


        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<String>
                } else {
                    val resultList = ArrayList<String>()
                    for (row in data) {
                        if (row.toString()?.toLowerCase(Locale.ROOT)
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
                dataFilterList = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }


}