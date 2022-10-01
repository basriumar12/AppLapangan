package com.alwin.applapangan.models.booking

import com.google.gson.annotations.SerializedName

data class BodyBooking(

	@field:SerializedName("jadwal_ids")
	val jadwalIds: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
