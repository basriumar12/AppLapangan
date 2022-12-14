package com.driver.nyaku.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}


fun currencyFormatter(num: String): String {

    val m = java.lang.Double.parseDouble(num)
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(m)
}

fun isValidEmailAddress(email: String): Boolean {
    val stricterFilter = true
    val stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"
    val laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*"
    val emailRegex = if (stricterFilter) stricterFilterString else laxString
    val p = java.util.regex.Pattern.compile(emailRegex)
    val m = p.matcher(email)
    return m.matches()
}

@SuppressLint("ServiceCast")
fun hasNetwork(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}


@RequiresApi(Build.VERSION_CODES.N)
fun parseDate(msg : String) : String{
    var result =""
    val dtStart = msg
    val converted: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        OffsetDateTime.parse(dtStart)
            .format(
                DateTimeFormatter.ofPattern(
                    "EEE MMM d HH:mm:ss",
                    Locale.ENGLISH
                )
            )


    } else {
        TODO("VERSION.SDK_INT < O")
    }
    println(converted)
    result = converted
    return  result.toString()
}

fun getPictureByteOfArray(bitmap: Bitmap): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream)
    return byteArrayOutputStream.toByteArray()
}

fun getBitmapFromByte(image: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(image, 0, image.size)
}

fun getBitmapFromURL(src: String): Bitmap? {
    try {
        val url = URL(src)
        val connection = url.openConnection() as HttpURLConnection
        connection.setDoInput(true)
        connection.connect()
        val input = connection.getInputStream()
        return BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

}