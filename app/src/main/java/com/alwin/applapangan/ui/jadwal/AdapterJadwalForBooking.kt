package com.alwin.applapangan.ui.jadwal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alwin.applapangan.R
import com.alwin.applapangan.models.gedung.JadwalsItem
import com.alwin.applapangan.utils.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.driver.nyaku.utils.currencyFormatter
import com.driver.nyaku.utils.invisible
import com.gorontalodigital.preference.Prefuser
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

    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(data: JadwalsItem, listiner: OnListener) {

            itemView.setOnLongClickListener {
                listiner.onClickGrup(data)
                itemView.card.setCardBackgroundColor(itemView.context.getResources().getColor(R.color.green));

                true
            }
            if (data.status == false)
                itemView.tv_date.text = "Jadwal : ${data.tanggal} / jam : ${data?.mulai} - ${data.selesai} "
            else
                itemView.invisible()



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
                        if (row.tanggal?.toString()?.toLowerCase(Locale.ROOT)
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