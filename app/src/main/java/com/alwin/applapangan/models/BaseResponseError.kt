package com.driver.nyaku.models

 data class BaseResponseEror (
     var message: String? = null,
     var status: Int?  = null,
     val errors: Any? = null

 )

