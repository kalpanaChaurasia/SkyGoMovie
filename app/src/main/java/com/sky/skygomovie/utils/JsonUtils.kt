package com.sky.skygomovie.utils

import android.util.Log
import java.io.IOException

object JsonUtils {

    fun readStringFromFile(fileName: String): String {
        return try {
            return javaClass.classLoader!!.getResourceAsStream(fileName).bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            Log.e("exception", "Exeption " + e.message)
            ""
        }
    }
}