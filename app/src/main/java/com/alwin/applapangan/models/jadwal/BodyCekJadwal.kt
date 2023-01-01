package com.alwin.applapangan.models.jadwal

import com.google.gson.annotations.SerializedName

data class BodyCekJadwal(

	@field:SerializedName("sampai_jam")
	val sampaiJam: String? = null,

	@field:SerializedName("jadwal_id")
	val jadwalId: String? = null,

	@field:SerializedName("dari_jam")
	val dariJam: String? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null
)
