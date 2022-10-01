package com.alwin.applapangan.models.login

import com.google.gson.annotations.SerializedName

data class BodyLogin(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
