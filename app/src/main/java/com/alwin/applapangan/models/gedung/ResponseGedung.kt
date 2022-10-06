package com.alwin.applapangan.models.gedung

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseGedung(

	@field:SerializedName("ResponseGedung")
	val responseGedung: List<ResponseGedungItem?>? = null
)

data class ResponseGedungItem(

	@field:SerializedName("provinsi")
	val provinsi: String? = null,

	@field:SerializedName("desa")
	val desa: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: String? = null,

	@field:SerializedName("lapangans")
	val lapangans: List<LapangansItem?>? = null,

	@field:SerializedName("kontak_pemilik")
	val kontakPemilik: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("kabupaten")
	val kabupaten: String? = null,

	@field:SerializedName("nama_gedung")
	val namaGedung: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)  : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.createTypedArrayList(LapangansItem),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(provinsi)
		parcel.writeString(desa)
		parcel.writeString(updatedAt)
		parcel.writeValue(userId)
		parcel.writeString(kecamatan)
		parcel.writeTypedList(lapangans)
		parcel.writeString(kontakPemilik)
		parcel.writeString(createdAt)
		parcel.writeValue(id)
		parcel.writeString(kabupaten)
		parcel.writeString(namaGedung)
		parcel.writeString(alamat)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResponseGedungItem> {
		override fun createFromParcel(parcel: Parcel): ResponseGedungItem {
			return ResponseGedungItem(parcel)
		}

		override fun newArray(size: Int): Array<ResponseGedungItem?> {
			return arrayOfNulls(size)
		}
	}
}

data class JadwalsItem(

	@field:SerializedName("lapangan_id")
	val lapanganId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("selesai")
	val selesai: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("mulai")
	val mulai: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(lapanganId)
		parcel.writeString(updatedAt)
		parcel.writeValue(userId)
		parcel.writeString(selesai)
		parcel.writeString(createdAt)
		parcel.writeString(mulai)
		parcel.writeValue(id)
		parcel.writeString(tanggal)
		parcel.writeValue(status)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<JadwalsItem> {
		override fun createFromParcel(parcel: Parcel): JadwalsItem {
			return JadwalsItem(parcel)
		}

		override fun newArray(size: Int): Array<JadwalsItem?> {
			return arrayOfNulls(size)
		}
	}
}

data class LapangansItem(

	@field:SerializedName("gedung_id")
	val gedungId: Int? = null,

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("waktu_buka")
	val waktuBuka: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("waktu_tutup")
	val waktuTutup: String? = null,

	@field:SerializedName("satuan")
	val satuan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("jadwals")
	val jadwals: List<JadwalsItem?>? = null,

	@field:SerializedName("nama_lapangan")
	val namaLapangan: String? = null
)  : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.createTypedArrayList(JadwalsItem),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(gedungId)
		parcel.writeString(harga)
		parcel.writeString(waktuBuka)
		parcel.writeString(updatedAt)
		parcel.writeValue(userId)
		parcel.writeString(waktuTutup)
		parcel.writeString(satuan)
		parcel.writeString(createdAt)
		parcel.writeValue(id)
		parcel.writeString(gambar)
		parcel.writeTypedList(jadwals)
		parcel.writeString(namaLapangan)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<LapangansItem> {
		override fun createFromParcel(parcel: Parcel): LapangansItem {
			return LapangansItem(parcel)
		}

		override fun newArray(size: Int): Array<LapangansItem?> {
			return arrayOfNulls(size)
		}
	}
}
