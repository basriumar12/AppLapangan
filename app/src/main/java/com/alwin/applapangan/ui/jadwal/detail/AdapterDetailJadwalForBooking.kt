package com.alwin.applapangan.ui.jadwal.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import kotlinx.android.synthetic.main.item_jadwal_for_booking.view.*
import java.util.*

class AdapterDetailJadwalForBooking(
    val context: Context, val data: MutableList<String>,
    private val itemListiner: OnListener
) :
    RecyclerView.Adapter<AdapterDetailJadwalForBooking.ViewHolder>(), Filterable {

    var dataFilterList = ArrayList<String>()

    init {
        dataFilterList = data as ArrayList<String>
    }

    companion object {
        const val ON_CLICK_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jadwal_for_booking, parent, false)

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
            itemView.tv_date.text = "Jam $data"
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
                    dataFilterList = data as ArrayList<String>
                } else {
                    val resultList = ArrayList<String>()
                    for (row in data) {
                        if (row?.toString()?.toLowerCase(Locale.ROOT)
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