package com.alwin.applapangan.ui.booking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.booking.ResponseBooking
import com.driver.nyaku.utils.currencyFormatter
import kotlinx.android.synthetic.main.item_transaksi.view.*
import java.util.*

class AdapterBooking(
    val context: Context, val data: MutableList<ResponseBooking>,
    private val itemListiner: OnListener
) :
    RecyclerView.Adapter<AdapterBooking.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<ResponseBooking>()

    init {
        dataFilterList = data as ArrayList<ResponseBooking>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataFilterList.size
    }

    interface OnListener {
        fun onClickGrup(data: ResponseBooking)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datas = dataFilterList[position]
        holder.bindView(datas, itemListiner)

    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: ResponseBooking, listiner: OnListener) {

            itemView.setOnClickListener {
                listiner.onClickGrup(data)
            }


            itemView.tv_name.text = "Nama lapangan : " + data.bookingDetails?.get(0)?.jadwal?.lapangan?.namaLapangan
            itemView.tv_status.text = "Status : " + data?.status
            itemView.tv_id.text = "Id Booking : " + data?.id
            itemView.tv_price.text = "Total : " + currencyFormatter(data?.totalBayar.toString())


        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilterList = data as ArrayList<ResponseBooking>
                } else {
                    val resultList = ArrayList<ResponseBooking>()
                    for (row in data) {
                        if (row.status.toString()?.toLowerCase(Locale.ROOT)
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
                dataFilterList = results?.values as ArrayList<ResponseBooking>
                notifyDataSetChanged()
            }

        }
    }


}