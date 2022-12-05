package com.alwin.applapangan.models.booking

import com.google.gson.annotations.SerializedName

data class BodyBookingNew(

	@field:SerializedName("sampai_jam")
	val sampaiJam: String? = null,

	@field:SerializedName("dari_jam")
	val dariJam: String? = null,

	@field:SerializedName("jadwal_ids")
	val jadwalIds: List<Int?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)
