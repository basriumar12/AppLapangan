package com.alwin.applapangan.ui.jadwal.detail_lapangan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.JadwalsItem
import com.driver.nyaku.utils.visible
import kotlinx.android.synthetic.main.item_jadwal_for_booking.view.*
import java.util.*

class AdapterJadwalForBooking(
    val context: Context, val data: MutableList<JadwalsItem>,
    private val itemListiner: OnListener
) :
    RecyclerView.Adapter<AdapterJadwalForBooking.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<JadwalsItem>()

    init {
        dataFilterList = data as ArrayList<JadwalsItem>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal_for_booking, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClickGrup(data: JadwalsItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)
        holder.itemView.tv_status.visible()
        if (datas.status.toString().equals("1")) {
            holder.itemView.tv_status.text = "Full Booking"
        } else {
            holder.itemView.tv_status.text = "Belum di Booking"
        }
        when (datas.kodeHari?.toInt()) {
            1 -> {
                holder.itemView.tv_date.text = " Hari Senin"
            }
            2 -> {
                holder.itemView.tv_date.text = " Hari Selasa"
            }
            3 -> {
                holder.itemView.tv_date.text = " Hari Rabu"
            }
            4 -> {
                holder.itemView.tv_date.text = " Hari Kamis"
            }
            5 -> {
                holder.itemView.tv_date.text = " Hari Jumat"
            }
            6 -> {
                holder.itemView.tv_date.text = " Hari Sabtu"
            }
            7 -> {
                holder.itemView.tv_date.text = " Hari Minggu"
            }
        }
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: JadwalsItem, listiner: OnListener) {

            itemView.setOnClickListener {
                if (data.status.toString().equals("1"))
                    Toast.makeText(itemView.context, "Full Boking", Toast.LENGTH_LONG).show()
                else
                    listiner.onClickGrup(data)
            }
            //hide
//            itemView.setOnLongClickListener {
//                listiner.onClickGrup(data)
//                itemView.card.setCardBackgroundColor(itemView.context.getResources().getColor(R.color.green));
//
//                true
//            }


        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<JadwalsItem>
                } else {
                    val resultList = ArrayList<JadwalsItem>()
                    for (row in data) {
                        if (row.status?.toString()?.toLowerCase(Locale.ROOT)
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
                dataFilterList = results?.values as ArrayList<JadwalsItem>
                notifyDataSetChanged()
            }

        }
    }


}