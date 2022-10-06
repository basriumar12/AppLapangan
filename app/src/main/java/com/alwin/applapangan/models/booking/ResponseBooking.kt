package com.alwin.applapangan.models.booking

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseBooking(

	@field:SerializedName("jadwal")
	val jadwal: Jadwal? = null,

	@field:SerializedName("jadwal_id")
	val jadwalId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readParcelable(Jadwal::class.java.classLoader),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readParcelable(User::class.java.classLoader),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeParcelable(jadwal, flags)
		parcel.writeValue(jadwalId)
		parcel.writeString(updatedAt)
		parcel.writeValue(userId)
		parcel.writeString(createdAt)
		parcel.writeString(id)
		parcel.writeParcelable(user, flags)
		parcel.writeString(status)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ResponseBooking> {
		override fun createFromParcel(parcel: Parcel): ResponseBooking {
			return ResponseBooking(parcel)
		}

		override fun newArray(size: Int): Array<ResponseBooking?> {
			return arrayOfNulls(size)
		}
	}
}

data class Lapangan(

	@field:SerializedName("gedung_id")
	val gedungId: Int? = null,

	@field:SerializedName("harga")
	val harga: String? = null,
	@field:SerializedName("gambar")
	val gambar: String? = null,

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
	val namaLapangan: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readParcelable(Gedung::class.java.classLoader),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(gedungId)
		parcel.writeString(harga)
		parcel.writeString(gambar)
		parcel.writeString(waktuBuka)
		parcel.writeString(updatedAt)
		parcel.writeValue(userId)
		parcel.writeString(waktuTutup)
		parcel.writeString(satuan)
		parcel.writeString(createdAt)
		parcel.writeParcelable(gedung, flags)
		parcel.writeValue(id)
		parcel.writeString(namaLapangan)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Lapangan> {
		override fun createFromParcel(parcel: Parcel): Lapangan {
			return Lapangan(parcel)
		}

		override fun newArray(size: Int): Array<Lapangan?> {
			return arrayOfNulls(size)
		}
	}
}

data class Jadwal(

	@field:SerializedName("lapangan_id")
	val lapanganId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("selesai")
	val selesai: String? = null,

	@field:SerializedName("lapangan")
	val lapangan: Lapangan? = null,

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
		parcel.readParcelable(Lapangan::class.java.classLoader),
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
		parcel.writeParcelable(lapangan, flags)
		parcel.writeString(createdAt)
		parcel.writeString(mulai)
		parcel.writeValue(id)
		parcel.writeString(tanggal)
		parcel.writeValue(status)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<Jadwal> {
		override fun createFromParcel(parcel: Parcel): Jadwal {
			return Jadwal(parcel)
		}

		override fun newArray(size: Int): Array<Jadwal?> {
			return arrayOfNulls(size)
		}
	}
}

data class User(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
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
		parcel.writeString(role)
		parcel.writeString(updatedAt)
		parcel.writeString(name)
		parcel.writeString(createdAt)
		parcel.writeValue(id)
		parcel.writeString(email)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<User> {
		override fun createFromParcel(parcel: Parcel): User {
			return User(parcel)
		}

		override fun newArray(size: Int): Array<User?> {
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
