package com.example.github.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPrefrenceHelper {
    companion object {
        private const val PREF_TIME = "pref_time"
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: SharedPrefrenceHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPrefrenceHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPrefrenceHelper()
        }

    }

    fun saveUpdateTime(time: String) {
        prefs?.edit(commit = true) { putString(PREF_TIME, time) }
    }

    fun getUpdateTime() = prefs?.getString(PREF_TIME, "2020-02-01T00:00:00Z")

}