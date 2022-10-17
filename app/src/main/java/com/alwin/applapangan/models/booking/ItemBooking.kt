package com.alwin.applapangan.models.booking

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ItemBooking(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("total_bayar")
	val totalBayar: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(updatedAt)
		parcel.writeValue(userId)
		parcel.writeString(createdAt)
		parcel.writeString(id)
		parcel.writeValue(totalBayar)
		parcel.writeString(status)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ItemBooking> {
		override fun createFromParcel(parcel: Parcel): ItemBooking {
			return ItemBooking(parcel)
		}

		override fun newArray(size: Int): Array<ItemBooking?> {
			return arrayOfNulls(size)
		}
	}
}
