package com.alwin.applapangan.models.booking

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
)

data class Lapangan(

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
	val namaLapangan: String? = null
)

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
)

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
	val emailVerifiedAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)

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
)
