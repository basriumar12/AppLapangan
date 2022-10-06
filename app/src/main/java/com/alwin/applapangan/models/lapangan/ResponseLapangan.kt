package com.alwin.applapangan.models.lapangan

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseLapangan(

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

	@field:SerializedName("gedung")
	val gedung: Gedung? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nama_lapangan")
	val namaLapangan: String? = null,

	@field:SerializedName("gambar")

	val gambar: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readParcelable(Gedung::class.java.classLoader),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
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
		parcel.writeParcelable(gedung, flags)
		parcel.writeValue(id)
		parcel.writeString(namaLapangan)
		parcel.writeString(gambar)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResponseLapangan> {
		override fun createFromParcel(parcel: Parcel): ResponseLapangan {
			return ResponseLapangan(parcel)
		}

		override fun newArray(size: Int): Array<ResponseLapangan?> {
			return arrayOfNulls(size)
		}
	}
}

data class Gedung(

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
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
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

	companion object CREATOR : Parcelable.Creator<Gedung> {
		override fun createFromParcel(parcel: Parcel): Gedung {
			return Gedung(parcel)
		}

		override fun newArray(size: Int): Array<Gedung?> {
			return arrayOfNulls(size)
		}
	}
}
