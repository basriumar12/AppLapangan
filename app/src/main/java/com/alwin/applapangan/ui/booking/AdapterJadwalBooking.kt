package com.alwin.applapangan.ui.booking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.BookingDetailsItem
import com.driver.nyaku.utils.invisible
import kotlinx.android.synthetic.main.item_jadwal.view.*
import kotlinx.android.synthetic.main.item_jadwal.view.tv_date
import kotlinx.android.synthetic.main.item_jadwal_for_booking.view.*
import java.util.*

class AdapterJadwalBooking(
    val context: Context, val data: MutableList<BookingDetailsItem>,
    private val itemListiner: OnListener
) :
    RecyclerView.Adapter<AdapterJadwalBooking.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<BookingDetailsItem>()

    init {
        dataFilterList = data as ArrayList<BookingDetailsItem>
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
        fun onClickGrup(data: BookingDetailsItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: BookingDetailsItem, listiner: OnListener) {

            itemView.setOnClickListener {
                listiner.onClickGrup(data)
            }


            itemView.tv_date.text = "Jadwal : ${data.jadwal?.tanggal} / jam : ${data?.jadwal?.mulai} - ${data.jadwal?.selesai} "

        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<BookingDetailsItem>
                } else {
                    val resultList = ArrayList<BookingDetailsItem>()
                    for (row in data) {
                        if (row.jadwal  .toString()?.toLowerCase(Locale.ROOT)
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
                dataFilterList = results?.values as ArrayList<BookingDetailsItem>
                notifyDataSetChanged()
            }

        }
    }


}