package com.alwin.applapangan.models.transaksi

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseTransaksi(

	@field:SerializedName("booking_id")
	val bookingId: String? = null,

	@field:SerializedName("bukti_bayar")
	val buktiBayar: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(bookingId)
		parcel.writeString(buktiBayar)
		parcel.writeString(updatedAt)
		parcel.writeValue(userId)
		parcel.writeString(createdAt)
		parcel.writeValue(id)
		parcel.writeString(status)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResponseTransaksi> {
		override fun createFromParcel(parcel: Parcel): ResponseTransaksi {
			return ResponseTransaksi(parcel)
		}

		override fun newArray(size: Int): Array<ResponseTransaksi?> {
			return arrayOfNulls(size)
		}
	}
}
