package com.alwin.applapangan.models.gedung

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseRekening(

	@field:SerializedName("nama_pemilik_akun")
	val namaPemilikAkun: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("no_rekening")
	val noRekening: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nama_bank")
	val namaBank: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(namaPemilikAkun)
		parcel.writeString(updatedAt)
		parcel.writeString(userId)
		parcel.writeString(noRekening)
		parcel.writeString(createdAt)
		parcel.writeValue(id)
		parcel.writeString(namaBank)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResponseRekening> {
		override fun createFromParcel(parcel: Parcel): ResponseRekening {
			return ResponseRekening(parcel)
		}

		override fun newArray(size: Int): Array<ResponseRekening?> {
			return arrayOfNulls(size)
		}
	}
}
